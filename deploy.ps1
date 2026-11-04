param([switch]$SkipBuild)
$ErrorActionPreference = 'Stop'
Set-StrictMode -Version Latest
$root = $PSScriptRoot
$settings = @{}
foreach ($line in [IO.File]::ReadAllLines((Join-Path $root '.env'))) {
    if ($line.Trim() -and !$line.TrimStart().StartsWith('#')) {
        $parts = $line -split '=', 2
        if ($parts.Count -eq 2) { $settings[$parts[0].Trim()] = $parts[1] }
    }
}
function Setting($name, $fallback) {
    if ($settings.ContainsKey($name) -and $settings[$name]) { return $settings[$name] }
    return $fallback
}
function Run($program, [string[]]$arguments) {
    & $program @arguments
    if ($LASTEXITCODE -ne 0) { throw "$program failed (exit $LASTEXITCODE)" }
}
$server = Setting 'SERVER_HOST' ''
$user = Setting 'SSH_USERNAME' ''
$port = Setting 'SSH_PORT' '22'
$appDir = Setting 'DEPLOY_DIR' '/opt/ruoyi'
$service = Setting 'DEPLOY_SERVICE' 'ruoyi'
$healthPort = Setting 'DEPLOY_HEALTH_PORT' '80'
if ($server -notmatch '^[a-zA-Z0-9.-]+$' -or $user -notmatch '^[a-zA-Z0-9_-]+$' -or $port -notmatch '^\d+$') { throw 'Invalid SSH settings in .env' }
if ($appDir -notmatch '^/[a-zA-Z0-9_/-]+$' -or $appDir -eq '/' -or $service -notmatch '^[a-zA-Z0-9_-]+$' -or $healthPort -notmatch '^\d+$') { throw 'Invalid deployment settings' }
$release = (Get-Date -Format 'yyyyMMdd-HHmmss') + '-' + [Guid]::NewGuid().ToString('N').Substring(0,8)
$temp = Join-Path $root "ruoyi-admin/target/deploy-$release"
$stage = "$appDir/releases/$release"
$utf8 = New-Object Text.UTF8Encoding $false
$savedEnv = @{}
foreach ($key in @('SSH_ASKPASS','SSH_ASKPASS_REQUIRE','DISPLAY','RUOYI_DEPLOY_PASSWORD','JAVA_HOME')) { $savedEnv[$key] = [Environment]::GetEnvironmentVariable($key,'Process') }
Push-Location $root
try {
    New-Item -ItemType Directory -Path $temp -Force | Out-Null
    if (!$SkipBuild) {
        $javaHome = Setting 'DEPLOY_JAVA_HOME' $env:JAVA_HOME
        if ($javaHome) { $env:JAVA_HOME = $javaHome }
        $maven = Setting 'DEPLOY_MAVEN' 'mvn.cmd'
        $arguments = @('-B','package')
        $repository = Setting 'DEPLOY_MAVEN_REPOSITORY' ''
        if ($repository) { $arguments += "-Dmaven.repo.local=$repository" }
        Write-Host '[1/4] Building and running project tests...'
        Run $maven $arguments
    }
    $jar = Join-Path $root 'ruoyi-admin/target/ruoyi-admin.jar'
    if (!(Test-Path $jar)) { throw 'Application JAR not found' }
    $hash = (Get-FileHash $jar -Algorithm SHA256).Hash.ToLowerInvariant()
    $runtime = @()
    foreach ($key in @('DB_URL','DB_USERNAME','DB_PASSWORD','DRUID_USERNAME','DRUID_PASSWORD')) {
        if (!$settings.ContainsKey($key) -or !$settings[$key]) { throw "Missing $key in .env" }
        $runtime += "$key=$($settings[$key])"
    }
    [IO.File]::WriteAllText((Join-Path $temp '.env'), ($runtime -join "`n") + "`n", $utf8)
    # Askpass sends the password only to OpenSSH, never in command arguments.
    $options = @('-o','StrictHostKeyChecking=yes','-o','ConnectTimeout=15','-o','ServerAliveInterval=15','-o','ServerAliveCountMax=4')
    $identity = Setting 'SSH_KEY_PATH' ''
    if ($identity) { $options += @('-i',$identity) }
    else {
        $env:RUOYI_DEPLOY_PASSWORD = Setting 'SSH_PASSWORD' ''
        if (!$env:RUOYI_DEPLOY_PASSWORD) { throw 'Set SSH_PASSWORD or SSH_KEY_PATH in .env' }
        [IO.File]::WriteAllText((Join-Path $temp 'askpass.ps1'), '[Console]::WriteLine($env:RUOYI_DEPLOY_PASSWORD)', $utf8)
        [IO.File]::WriteAllText((Join-Path $temp 'askpass.cmd'), "@echo off`r`npowershell.exe -NoProfile -ExecutionPolicy Bypass -File `"%~dp0askpass.ps1`"`r`n", $utf8)
        $env:SSH_ASKPASS = Join-Path $temp 'askpass.cmd'
        $env:SSH_ASKPASS_REQUIRE = 'force'
        $env:DISPLAY = 'ruoyi-deploy'
        $options += @('-o','PreferredAuthentications=password','-o','NumberOfPasswordPrompts=1')
    }
    $destination = "$user@$server"
    Write-Host '[2/4] Uploading release...'
    Run 'ssh.exe' (@('-n','-p',$port) + $options + @($destination,"umask 077; mkdir -p '$stage'"))
    Run 'scp.exe' (@('-P',$port) + $options + @($jar,(Join-Path $temp '.env'),(Join-Path $root 'scripts/deploy-remote.sh'),"${destination}:$stage/"))
    Write-Host '[3/4] Verifying, backing up, restarting and checking health...'
    Run 'ssh.exe' (@('-n','-p',$port) + $options + @($destination,"bash '$stage/deploy-remote.sh' '$appDir' '$service' '$healthPort' '$release' '$hash'"))
    Write-Host '[4/4] Checking public login page...'
    Run 'curl.exe' @('--fail','--silent','--show-error','--connect-timeout','15','--max-time','30','-o','NUL',"http://${server}:$healthPort/login")
    $result = "SUCCESS: $release`nSHA256: $hash`nURL: http://${server}:$healthPort/login`nBackup: $appDir/backups/$release"
    [IO.File]::WriteAllText((Join-Path $root 'ruoyi-admin/target/deploy-result.txt'),$result,$utf8)
    Write-Host $result
} catch {
    $failurePath = Join-Path $root 'ruoyi-admin/target/deploy-result.txt'
    if (Test-Path (Split-Path $failurePath)) {
        [IO.File]::WriteAllText($failurePath,"FAILED: $release`n$($_.Exception.Message)",$utf8)
    }
    Write-Error "Deployment failed: $($_.Exception.Message)" -ErrorAction Continue
    exit 1
} finally {
    foreach ($key in $savedEnv.Keys) { [Environment]::SetEnvironmentVariable($key,$savedEnv[$key],'Process') }
    # Only delete the temporary runtime secret, never application backups.
    $secret = Join-Path $temp '.env'
    if (Test-Path -LiteralPath $secret) { Remove-Item -LiteralPath $secret -Force }
    Pop-Location
}

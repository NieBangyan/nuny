#!/usr/bin/env bash
set -Eeuo pipefail
umask 077
app_dir=$1
service=$2
port=$3
release=$4
expected=$5
stage="$app_dir/releases/$release"
backup="$app_dir/backups/$release"
exec 9>"$app_dir/.deploy.lock"
flock -n 9 || { echo 'Another deployment is running'; exit 1; }
test -f "$app_dir/ruoyi-admin.jar"
systemctl cat "$service" >/dev/null
actual=$(sha256sum "$stage/ruoyi-admin.jar" | cut -d ' ' -f 1)
test "$actual" = "$expected" || { echo 'JAR checksum mismatch'; exit 1; }
mkdir -p "$backup"
cp -p "$app_dir/ruoyi-admin.jar" "$backup/ruoyi-admin.jar"
had_env=0
if test -f "$app_dir/.env"; then
    cp -p "$app_dir/.env" "$backup/.env"
    had_env=1
fi
health() {
    for attempt in $(seq 1 45); do
        if systemctl is-active --quiet "$service" && curl --fail --silent --max-time 4 "http://127.0.0.1:$port/login" | grep 'login' >/dev/null; then
            return 0
        fi
        sleep 2
    done
    return 1
}
rollback() {
    trap - ERR
    echo 'Deployment failed; restoring previous release...'
    cp -p "$backup/ruoyi-admin.jar" "$app_dir/ruoyi-admin.jar"
    if test "$had_env" = 1; then
        cp -p "$backup/.env" "$app_dir/.env"
    else
        rm -f -- "$app_dir/.env"
    fi
    systemctl restart "$service"
    if health; then echo 'ROLLBACK OK'; else echo 'ROLLBACK FAILED: check service logs'; fi
    exit 1
}
trap rollback ERR
install -m 600 "$stage/.env" "$app_dir/.env"
install -m 600 "$stage/ruoyi-admin.jar" "$app_dir/ruoyi-admin.jar"
systemctl restart "$service"
health
trap - ERR
echo "SERVER OK: release=$release service=$service sha256=$actual backup=$backup"

package com.ruoyi.web.controller.system;

import java.time.LocalDate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.mapper.ClassroomReportMapper;

@RestController
@RequestMapping("/system/classroom")
public class ClassroomReportController {
    @Autowired private ClassroomReportMapper mapper;

    @GetMapping("/report")
    @RequiresPermissions("system:records:list")
    public AjaxResult report(@RequestParam String date) {
        try { LocalDate.parse(date); }
        catch (RuntimeException e) { return AjaxResult.error("请选择有效日期"); }
        return AjaxResult.success(mapper.report(date));
    }

    @PostMapping("/students")
    @RequiresPermissions("system:records:add")
    public AjaxResult add(@RequestParam String name, @RequestParam int row) {
        name = name.trim();
        if (name.isEmpty() || name.length() > 50 || row < 1 || row > 100)
            return AjaxResult.error("姓名为1至50字，排号为1至100");
        try { mapper.add(name, row); }
        catch (DuplicateKeyException e) { return AjaxResult.error("该姓名已存在；同名学生请添加学号后缀区分"); }
        return AjaxResult.success();
    }

    @PostMapping("/move")
    @RequiresPermissions("system:records:edit")
    public AjaxResult move(@RequestParam long id, @RequestParam int row) {
        if (row < 1 || row > 100) return AjaxResult.error("排号为1至100");
        return mapper.move(id,row) > 0 ? AjaxResult.success() : AjaxResult.error("学生不存在");
    }

    @PostMapping("/mark")
    @RequiresPermissions("system:records:edit")
    public AjaxResult mark(@RequestParam long id, @RequestParam String date, @RequestParam int completed) {
        try { LocalDate.parse(date); }
        catch (RuntimeException e) { return AjaxResult.error("请选择有效日期"); }
        if (completed != 0 && completed != 1) return AjaxResult.error("完成状态无效");
        return mapper.mark(id,date,completed) > 0 ? AjaxResult.success() : AjaxResult.error("学生不存在");
    }
}

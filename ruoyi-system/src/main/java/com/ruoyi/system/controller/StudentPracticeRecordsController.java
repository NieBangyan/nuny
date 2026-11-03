package com.ruoyi.system.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.StudentPracticeRecords;
import com.ruoyi.system.service.IStudentPracticeRecordsService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 课堂记录Controller
 * 
 * @author ruoyi
 * @date 2026-11-02
 */
@Controller
@RequestMapping("/system/records")
public class StudentPracticeRecordsController extends BaseController
{
    private String prefix = "system/records";

    @Autowired
    private IStudentPracticeRecordsService studentPracticeRecordsService;

    @RequiresPermissions("system:records:view")
    @GetMapping()
    public String records()
    {
        return prefix + "/records";
    }

    /**
     * 查询课堂记录列表
     */
    @RequiresPermissions("system:records:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StudentPracticeRecords studentPracticeRecords)
    {
        startPage();
        List<StudentPracticeRecords> list = studentPracticeRecordsService.selectStudentPracticeRecordsList(studentPracticeRecords);
        return getDataTable(list);
    }

    /**
     * 导出课堂记录列表
     */
    @RequiresPermissions("system:records:export")
    @Log(title = "课堂记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StudentPracticeRecords studentPracticeRecords)
    {
        List<StudentPracticeRecords> list = studentPracticeRecordsService.selectStudentPracticeRecordsList(studentPracticeRecords);
        ExcelUtil<StudentPracticeRecords> util = new ExcelUtil<StudentPracticeRecords>(StudentPracticeRecords.class);
        return util.exportExcel(list, "课堂记录数据");
    }

    /**
     * 查看课堂记录详情
     */
    @RequiresPermissions("system:records:view")
    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Long id, ModelMap mmap)
    {
        StudentPracticeRecords studentPracticeRecords = studentPracticeRecordsService.selectStudentPracticeRecordsById(id);
        mmap.put("studentPracticeRecords", studentPracticeRecords);
        return prefix + "/view";
    }

    /**
     * 新增课堂记录
     */
    @RequiresPermissions("system:records:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存课堂记录
     */
    @RequiresPermissions("system:records:add")
    @Log(title = "课堂记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StudentPracticeRecords studentPracticeRecords)
    {
        return toAjax(studentPracticeRecordsService.insertStudentPracticeRecords(studentPracticeRecords));
    }

    /**
     * 修改课堂记录
     */
    @RequiresPermissions("system:records:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        StudentPracticeRecords studentPracticeRecords = studentPracticeRecordsService.selectStudentPracticeRecordsById(id);
        mmap.put("studentPracticeRecords", studentPracticeRecords);
        return prefix + "/edit";
    }

    /**
     * 修改保存课堂记录
     */
    @RequiresPermissions("system:records:edit")
    @Log(title = "课堂记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StudentPracticeRecords studentPracticeRecords)
    {
        return toAjax(studentPracticeRecordsService.updateStudentPracticeRecords(studentPracticeRecords));
    }

    /**
     * 删除课堂记录
     */
    @RequiresPermissions("system:records:remove")
    @Log(title = "课堂记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(studentPracticeRecordsService.deleteStudentPracticeRecordsByIds(ids));
    }
}

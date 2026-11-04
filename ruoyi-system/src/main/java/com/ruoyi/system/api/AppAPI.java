package com.ruoyi.system.api;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.IStudentPracticeRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/student")
public class AppAPI {
    @Autowired
    private IStudentPracticeRecordsService studentPracticeRecordsService;

    @GetMapping("/chart")
    @ResponseBody
    public AjaxResult studentChart() {
        List<Map<String, Object>> maps = studentPracticeRecordsService.studentChart();
        return AjaxResult.success(maps);
    }
}

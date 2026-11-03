package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 课堂记录对象 student_practice_records
 * 
 * @author ruoyi
 * @date 2026-11-02
 */
public class StudentPracticeRecords extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键，自动增长 */
    private Long id;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String studentName;

    /** 是否完成：1-已完成，0-未完成 */
    @Excel(name = "是否完成：1-已完成，0-未完成")
    private Integer isCompleted;

    /** 练习日期 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "练习日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date practiceDate;

    /** 记录创建时间 */
    private Date createdAt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setStudentName(String studentName) 
    {
        this.studentName = studentName;
    }

    public String getStudentName() 
    {
        return studentName;
    }

    public void setIsCompleted(Integer isCompleted) 
    {
        this.isCompleted = isCompleted;
    }

    public Integer getIsCompleted() 
    {
        return isCompleted;
    }

    public void setPracticeDate(Date practiceDate) 
    {
        this.practiceDate = practiceDate;
    }

    public Date getPracticeDate() 
    {
        return practiceDate;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("studentName", getStudentName())
            .append("isCompleted", getIsCompleted())
            .append("practiceDate", getPracticeDate())
            .append("remark", getRemark())
            .append("createdAt", getCreatedAt())
            .toString();
    }
}

package com.ruoyi.model;

import java.time.LocalDate;

public class Student_practice_records {
    private Long id;
    private String studentName;
    private Integer isCompleted;
    private LocalDate practiceDate;
    private String remark;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public Integer getIsCompleted() { return isCompleted; }
    public void setIsCompleted(Integer isCompleted) { this.isCompleted = isCompleted; }
    public LocalDate getPracticeDate() { return practiceDate; }
    public void setPracticeDate(LocalDate practiceDate) { this.practiceDate = practiceDate; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }


}

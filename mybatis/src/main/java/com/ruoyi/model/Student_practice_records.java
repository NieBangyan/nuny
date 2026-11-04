package com.ruoyi.model;

import java.util.Date;

public class Student_practice_records {
    private Long id;
    private String studentName;
    private Integer isCompleted;
    private Date practiceDate;
    private String remark;
    private Date createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public Integer getIsCompleted() { return isCompleted; }
    public void setIsCompleted(Integer isCompleted) { this.isCompleted = isCompleted; }
    public Date getPracticeDate() { return practiceDate; }
    public void setPracticeDate(Date practiceDate) { this.practiceDate = practiceDate; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Student_practice_records{" +
                "id=" + id +
                ", student_name='" + studentName + '\'' +
                ", is_completed=" + isCompleted +
                ", practice_date=" + practiceDate +
                ", remark='" + remark + '\'' +
                ", created_at=" + createdAt +
                '}';
    }
}

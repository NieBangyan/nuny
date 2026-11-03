package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.StudentPracticeRecords;

/**
 * 课堂记录Mapper接口
 * 
 * @author ruoyi
 * @date 2026-11-02
 */
public interface StudentPracticeRecordsMapper 
{
    /**
     * 查询课堂记录
     * 
     * @param id 课堂记录主键
     * @return 课堂记录
     */
    public StudentPracticeRecords selectStudentPracticeRecordsById(Long id);

    /**
     * 查询课堂记录列表
     * 
     * @param studentPracticeRecords 课堂记录
     * @return 课堂记录集合
     */
    public List<StudentPracticeRecords> selectStudentPracticeRecordsList(StudentPracticeRecords studentPracticeRecords);

    /**
     * 新增课堂记录
     * 
     * @param studentPracticeRecords 课堂记录
     * @return 结果
     */
    public int insertStudentPracticeRecords(StudentPracticeRecords studentPracticeRecords);

    /**
     * 修改课堂记录
     * 
     * @param studentPracticeRecords 课堂记录
     * @return 结果
     */
    public int updateStudentPracticeRecords(StudentPracticeRecords studentPracticeRecords);

    /**
     * 删除课堂记录
     * 
     * @param id 课堂记录主键
     * @return 结果
     */
    public int deleteStudentPracticeRecordsById(Long id);

    /**
     * 批量删除课堂记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudentPracticeRecordsByIds(String[] ids);
}

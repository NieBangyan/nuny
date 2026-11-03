package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.StudentPracticeRecordsMapper;
import com.ruoyi.system.domain.StudentPracticeRecords;
import com.ruoyi.system.service.IStudentPracticeRecordsService;
import com.ruoyi.common.core.text.Convert;

/**
 * 课堂记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-11-02
 */
@Service
public class StudentPracticeRecordsServiceImpl implements IStudentPracticeRecordsService 
{
    @Autowired
    private StudentPracticeRecordsMapper studentPracticeRecordsMapper;

    /**
     * 查询课堂记录
     * 
     * @param id 课堂记录主键
     * @return 课堂记录
     */
    @Override
    public StudentPracticeRecords selectStudentPracticeRecordsById(Long id)
    {
        return studentPracticeRecordsMapper.selectStudentPracticeRecordsById(id);
    }

    /**
     * 查询课堂记录列表
     * 
     * @param studentPracticeRecords 课堂记录
     * @return 课堂记录
     */
    @Override
    public List<StudentPracticeRecords> selectStudentPracticeRecordsList(StudentPracticeRecords studentPracticeRecords)
    {
        return studentPracticeRecordsMapper.selectStudentPracticeRecordsList(studentPracticeRecords);
    }

    /**
     * 新增课堂记录
     * 
     * @param studentPracticeRecords 课堂记录
     * @return 结果
     */
    @Override
    public int insertStudentPracticeRecords(StudentPracticeRecords studentPracticeRecords)
    {
        return studentPracticeRecordsMapper.insertStudentPracticeRecords(studentPracticeRecords);
    }

    /**
     * 修改课堂记录
     * 
     * @param studentPracticeRecords 课堂记录
     * @return 结果
     */
    @Override
    public int updateStudentPracticeRecords(StudentPracticeRecords studentPracticeRecords)
    {
        return studentPracticeRecordsMapper.updateStudentPracticeRecords(studentPracticeRecords);
    }

    /**
     * 批量删除课堂记录
     * 
     * @param ids 需要删除的课堂记录主键
     * @return 结果
     */
    @Override
    public int deleteStudentPracticeRecordsByIds(String ids)
    {
        return studentPracticeRecordsMapper.deleteStudentPracticeRecordsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除课堂记录信息
     * 
     * @param id 课堂记录主键
     * @return 结果
     */
    @Override
    public int deleteStudentPracticeRecordsById(Long id)
    {
        return studentPracticeRecordsMapper.deleteStudentPracticeRecordsById(id);
    }
}

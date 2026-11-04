package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Map;
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
    @Override
    public List<Map<String, Object>> studentChart()
    {
        return studentPracticeRecordsMapper.selectStudentChart();
    }

    @Autowired
    private StudentPracticeRecordsMapper studentPracticeRecordsMapper;
    @Autowired
    private com.ruoyi.system.mapper.ClassroomReportMapper classroomMapper;

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
    @org.springframework.transaction.annotation.Transactional
    public int insertStudentPracticeRecords(StudentPracticeRecords studentPracticeRecords)
    {
        validateRecord(studentPracticeRecords);
        classroomMapper.ensureStudent(studentPracticeRecords.getStudentName(), studentPracticeRecords.getSeatRow());
        Long existing = classroomMapper.latest(studentPracticeRecords.getStudentName(), studentPracticeRecords.getPracticeDate());
        if (existing != null) {
            studentPracticeRecords.setId(existing);
            return studentPracticeRecordsMapper.updateStudentPracticeRecords(studentPracticeRecords);
        }
        return studentPracticeRecordsMapper.insertStudentPracticeRecords(studentPracticeRecords);
    }

    /**
     * 修改课堂记录
     * 
     * @param studentPracticeRecords 课堂记录
     * @return 结果
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public int updateStudentPracticeRecords(StudentPracticeRecords studentPracticeRecords)
    {
        validateRecord(studentPracticeRecords);
        classroomMapper.ensureStudent(studentPracticeRecords.getStudentName(), studentPracticeRecords.getSeatRow());
        return studentPracticeRecordsMapper.updateStudentPracticeRecords(studentPracticeRecords);
    }

    private void validateRecord(StudentPracticeRecords record) {
        if (record.getStudentName() == null || record.getStudentName().trim().isEmpty() || record.getStudentName().trim().length() > 50
                || record.getPracticeDate() == null || record.getIsCompleted() == null || (record.getIsCompleted() != 0 && record.getIsCompleted() != 1)
                || (record.getSeatRow() != null && (record.getSeatRow() < 1 || record.getSeatRow() > 100))) {
            throw new com.ruoyi.common.exception.ServiceException("请填写有效的姓名、练习日期、完成状态和排号（1—100）");
        }
        record.setStudentName(record.getStudentName().trim());
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

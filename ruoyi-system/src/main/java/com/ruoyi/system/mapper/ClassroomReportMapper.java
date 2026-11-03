package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.*;

public interface ClassroomReportMapper {
    @Select("SELECT s.id, s.student_name AS name, s.seat_row AS rowNumber, COALESCE((SELECT p.is_completed FROM student_practice_records p WHERE p.student_name=s.student_name AND p.practice_date=#{date} ORDER BY p.id DESC LIMIT 1),0) AS completed FROM classroom_student s ORDER BY s.seat_row,s.id")
    List<Map<String,Object>> report(String date);

    @Insert("INSERT INTO classroom_student(student_name,seat_row) VALUES(#{name},#{row})")
    int add(@Param("name") String name, @Param("row") int row);

    @Update("UPDATE classroom_student SET seat_row=#{row} WHERE id=#{id}")
    int move(@Param("id") long id, @Param("row") int row);

    @Insert("INSERT INTO student_practice_records(student_name,is_completed,practice_date,created_at) SELECT student_name,#{completed},#{date},NOW() FROM classroom_student WHERE id=#{id}")
    int mark(@Param("id") long id, @Param("date") String date, @Param("completed") int completed);
}

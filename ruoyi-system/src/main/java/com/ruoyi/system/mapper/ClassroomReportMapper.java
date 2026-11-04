package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.*;

public interface ClassroomReportMapper {
    @Insert("INSERT INTO classroom_student(student_name,seat_row) VALUES(#{name},COALESCE(#{row},0)) ON DUPLICATE KEY UPDATE seat_row=COALESCE(#{row},seat_row)")
    int ensureStudent(@Param("name") String name, @Param("row") Integer row);

    @Select("SELECT id FROM student_practice_records WHERE student_name=#{name} AND practice_date >= #{date} AND practice_date < DATE_ADD(#{date},INTERVAL 1 DAY) ORDER BY id DESC LIMIT 1")
    Long latest(@Param("name") String name, @Param("date") java.util.Date date);
    @Select("SELECT COALESCE(s.id,-p.id) AS id, p.student_name AS name, COALESCE(s.seat_row,0) AS rowNumber, p.is_completed AS completed FROM student_practice_records p LEFT JOIN classroom_student s ON s.student_name=p.student_name WHERE p.practice_date >= #{date} AND p.practice_date < DATE_ADD(#{date},INTERVAL 1 DAY) AND p.id=(SELECT MAX(p2.id) FROM student_practice_records p2 WHERE p2.student_name=p.student_name AND p2.practice_date >= #{date} AND p2.practice_date < DATE_ADD(#{date},INTERVAL 1 DAY)) ORDER BY rowNumber,p.id")
    List<Map<String,Object>> report(String date);
    @Insert("INSERT INTO classroom_student(student_name,seat_row) VALUES(#{name},#{row})")
    int add(@Param("name") String name, @Param("row") int row);

    @Update("UPDATE classroom_student SET seat_row=#{row} WHERE id=#{id}")
    int move(@Param("id") long id, @Param("row") int row);

    @Update("UPDATE student_practice_records SET is_completed=#{completed} WHERE student_name=(SELECT student_name FROM classroom_student WHERE id=#{id}) AND practice_date >= #{date} AND practice_date < DATE_ADD(#{date},INTERVAL 1 DAY)")
    int mark(@Param("id") long id, @Param("date") String date, @Param("completed") int completed);
}

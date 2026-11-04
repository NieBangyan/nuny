package com.ruoyi;

import com.ruoyi.model.Student_practice_records;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
             SqlSession sqlSession = new SqlSessionFactoryBuilder()
                     .build(reader, DatabaseProperties.load()).openSession()) {
            List<Student_practice_records> list = sqlSession.selectList(
                    "com.ruoyi.mapper.StudentPracticeRecordsMapper.selectRecords");
            for (Student_practice_records studentPracticeRecords : list) {
                System.out.println(studentPracticeRecords);
            }
        }
    }
}

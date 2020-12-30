package com.wang.mapper;

import com.wang.entity.Student;
import com.wang.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author 19225
 * @create 2020/12/23 17:46
 */
public interface StudentMapper {
    /*@Select("select * from mybatis.teacher where id = #{id}")
    Teacher getTeacher(@Param("id") int id);*/
    //查询所有学生
    List<Student> getStudent();

    List<Student> getStudent2();
}

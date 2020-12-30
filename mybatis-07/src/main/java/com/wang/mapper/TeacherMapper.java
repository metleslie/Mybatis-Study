package com.wang.mapper;

import com.wang.entity.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 19225
 * @create 2020/12/23 17:46
 */
public interface TeacherMapper {
    Teacher getTeacher(@Param("tid") int id);
    Teacher getTeacher2(@Param("tid") int id);
}

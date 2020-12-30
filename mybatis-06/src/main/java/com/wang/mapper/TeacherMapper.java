package com.wang.mapper;

import com.wang.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author 19225
 * @create 2020/12/23 17:46
 */
public interface TeacherMapper {
    List<Teacher> getTeacher();
}

package com.wang.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author 19225
 * @create 2020/12/23 17:44
 */
@Data
public class Teacher {
    private int id;
    private String name;
    private List<Student> students;
}

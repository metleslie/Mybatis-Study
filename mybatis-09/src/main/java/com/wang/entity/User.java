package com.wang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author 19225
 * @create 2020/12/10 20:48
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    private int id;
    private String name;
    private String pwd;
}

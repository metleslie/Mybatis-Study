package com.wang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * @Author 19225
 * @create 2020/12/10 20:48
 */

@Data

public class User {
    private int id;
    private String name;
    private String password;
}

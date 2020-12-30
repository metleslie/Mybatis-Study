package com.wang.mapper;

import com.wang.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @Author 19225
 * @create 2020/12/27 17:53
 */
public interface UserMapper {
    List<User> getUser();
    User queryUser(@Param("id") int id);
    int updateUser(HashMap map);
}

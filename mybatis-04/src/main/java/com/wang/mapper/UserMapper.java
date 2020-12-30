package com.wang.mapper;

import com.sun.javafx.collections.MappingChange;
import com.wang.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @Author 19225
 * @create 2020/12/10 20:48
 */
public interface UserMapper {
    //查询所有用户
    List<User> getUserList();
    //根据id查找用户
    User getUserById(int id);
    //用map添加用户
    int addUser(Map<String,Object> map);
    //分页查询
    List<User> getUserLimit(Map<String,Integer> map);
    //分页查询2
    List<User> getUserLimitByRowBounds();

}

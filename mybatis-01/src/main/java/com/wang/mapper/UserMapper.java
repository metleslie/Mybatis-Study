package com.wang.mapper;

import com.wang.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @Author 19225
 * @create 2020/12/10 20:48
 */
public interface UserMapper {
    //模糊查询
    List<User> getLike(String value);
    //查询所有用户
   public List<User> getUserList();
    //根据id查询用户
   public User getUserById(int id);
    //添加用户
   public int addUser(User user);

   //万能的map
    int addUser2(Map<String,Object> map);

    //map修改用户
    int change2(Map<String,Object> map);
    //修改用户
   public int change(User user);
    //删除用户
   public int deleteUser(int id);
}

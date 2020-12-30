package com.wang.mapper;

import com.wang.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @Author 19225
 * @create 2020/12/28 17:52
 */
public interface UserMapper {
    List<User> getUser();

    //通过userCode获取User
    User getLoginUser(@Param("userCode") String userCode);

    //增加用户信息
    int addUser(HashMap map);

    //通过条件查询
    List<User> getUserList(@Param("userName") String userName,
                           @Param("userRole") Integer userRole,
                           @Param("currentPageNo") Integer currentPageNo,
                           @Param("pageSize") Integer pageSize);
    //通过条件查询，用户表记录数
    int getUserCount(@Param("userName") String userName,
                     @Param("userRole") Integer userRole);

    //通过UserID删除用户
    int deleteUserById(@Param("id") int id);

    //通过userId获取用户
    User getUserById(@Param("id") Integer id);

    //修改用户信息
    int changeUser(User user);

    //修改当前用户密码
    int updatePwd(@Param("id") Integer id,@Param("userPassword") String pwd);
}

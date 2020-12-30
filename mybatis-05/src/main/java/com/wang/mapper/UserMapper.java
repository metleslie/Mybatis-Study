package com.wang.mapper;

import com.wang.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Author 19225
 * @create 2020/12/10 20:48
 */
public interface UserMapper {
    //查询所有用户
    @Select("select * from mybatis.user")
    List<User> getUserList();

    //根据id查询用户
    //方法存在多个参数，所有的参数面前都需要加上@param注释
    @Select("select * from user where id = #{id}")
    User getUserById(@Param("id") int id);

    //增加用户
    @Insert("insert into user(id,name,pwd) values(#{id},#{name},#{pwd})")
    int addUser(User user);

    //修改用户
    @Update("update user set name = #{name},pwd = #{pwd} where id = #{id}")
    int update(User user);

    //删除用户
    @Delete("delete from user where id = #{iud}")
    int deleteUser(@Param("iud") int id);
}

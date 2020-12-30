package com.wang.mapper;

import com.wang.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 19225
 * @create 2020/12/29 13:24
 */
public interface RoleMapper {
    //获取角色列表
    public List<Role> getRoleList()throws Exception;

    //增加角色信息
    public int add(Role role)throws Exception;

    //通过Id删除Role
    public int deleteRoleById(@Param("id") String Id)throws Exception;

    //修改角色信息
    public int modify(Role role)throws Exception;

    //通过Id获取role
    public Role getRoleById(@Param("id") Integer id)throws Exception;

    //根据roleCode，进行角色编码的唯一性验证(统计count)
    public int roleCodeIsExist(@Param("roleCode") String roleCode)throws Exception;
}

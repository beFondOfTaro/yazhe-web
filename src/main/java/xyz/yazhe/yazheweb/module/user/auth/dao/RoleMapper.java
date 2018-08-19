package xyz.yazhe.yazheweb.module.user.auth.dao;


import org.apache.ibatis.annotations.Param;
import xyz.yazhe.yazheweb.module.user.auth.POJO.DO.Role;
import xyz.yazhe.yazheweb.module.user.auth.POJO.DO.RolePermission;
import xyz.yazhe.yazheweb.module.user.auth.POJO.DTO.RoleDTO;

import java.util.List;

/**
 * @author BeFondOfTaro
 * Created at 19:09 2018/5/14
 */
public interface RoleMapper {

    /**
     * 插入角色信息
     * @param role 角色
     * @return
     */
    int insertRole(Role role);

    /**
     * 插入角色权限关联表信息
     * @param rolePermission 角色权限关联信息
     * @return
     */
    int insertRolePermission(RolePermission rolePermission);

    /**
     * 根据id删除角色
     * @param roleId 角色id
     * @return
     */
    int deleteRoleById(@Param("roleId") String roleId);

    /**
     * 根据角色id删除角色权限关联信息
     * @param roleId 角色id
     * @return
     */
    int deleteRolePermissionByRoleId(@Param("roleId") String roleId);

    /**
     * 查询所有角色信息
     * @return
     */
    List<RoleDTO> listRole();
}

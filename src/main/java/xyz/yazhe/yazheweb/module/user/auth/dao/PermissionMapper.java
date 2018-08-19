package xyz.yazhe.yazheweb.module.user.auth.dao;


import xyz.yazhe.yazheweb.module.user.auth.POJO.DO.Permission;

import java.util.List;

/**
 * @author BeFondOfTaro
 * Created at 13:31 2018/5/18
 */
public interface PermissionMapper {

    /**
     * 查询所有权限
     * @return
     */
    List<Permission> listPermission();
}

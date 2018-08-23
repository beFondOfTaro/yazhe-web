package xyz.yazhe.yazheweb.service.user.auth.service;



import xyz.yazhe.yazheweb.service.domain.user.auth.DO.Permission;

import java.util.List;

/**
 * 权限服务
 * @author BeFondOfTaro
 * Created at 13:28 2018/5/18
 */
public interface PermissionService {

    /**
     * 查询所有权限
     * @return
     */
    List<Permission> listPermission();
}

package xyz.yazhe.yazheweb.module.user.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.yazhe.yazheweb.module.user.auth.POJO.DO.Permission;
import xyz.yazhe.yazheweb.module.user.auth.dao.PermissionMapper;
import xyz.yazhe.yazheweb.module.user.auth.service.PermissionService;

import java.util.List;

/**
 * @author BeFondOfTaro
 * Created at 13:30 2018/5/18
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> listPermission() {
        return permissionMapper.listPermission();
    }
}

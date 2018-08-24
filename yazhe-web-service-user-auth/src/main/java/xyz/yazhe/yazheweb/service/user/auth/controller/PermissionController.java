package xyz.yazhe.yazheweb.service.user.auth.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.yazhe.yazheweb.service.domain.base.ResultVO;
import xyz.yazhe.yazheweb.service.domain.common.constants.ResourceConstants;
import xyz.yazhe.yazheweb.service.user.auth.service.PermissionService;
import xyz.yazhe.yazheweb.service.util.web.result.ResultVOUtil;


/**
 * 权限操作
 * @author BeFondOfTaro
 * Created at 13:34 2018/5/18
 */
@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 查询所有权限
     * @return
     */
    @GetMapping(ResourceConstants.PERMISSION)
    public ResultVO listPermission(){
        return ResultVOUtil.success(permissionService.listPermission());
    }
}

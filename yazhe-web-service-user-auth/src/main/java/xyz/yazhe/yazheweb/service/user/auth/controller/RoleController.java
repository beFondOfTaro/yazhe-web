package xyz.yazhe.yazheweb.service.user.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.yazhe.yazheweb.service.domain.base.QueryPage;
import xyz.yazhe.yazheweb.service.domain.base.ResultVO;
import xyz.yazhe.yazheweb.service.domain.common.constants.ResourceConstants;
import xyz.yazhe.yazheweb.service.domain.user.auth.DTO.RoleAddDTO;
import xyz.yazhe.yazheweb.service.user.auth.service.RoleService;
import xyz.yazhe.yazheweb.service.util.web.result.ResultVOUtil;

import java.util.List;

/**
 * 角色操作
 * @author BeFondOfTaro
 * Created at 23:41 2018/5/15
 */
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 添加角色
     * @param roleAddDTO 角色信息
     */
    @PostMapping(ResourceConstants.ROLE)
    public ResultVO addRole(RoleAddDTO roleAddDTO){
        roleService.addRole(roleAddDTO);
        return ResultVOUtil.success();
    }

    /**
     * 根据角色id删除角色
     * @param roleId 角色id
     */
    @DeleteMapping(ResourceConstants.ROLE + "/{roleId}")
    public ResultVO deleteRoleById(@PathVariable String roleId){
        roleService.deleteRoleById(roleId);
        return ResultVOUtil.success();
    }

    /**
     * 更新角色的权限
     * @param roleId 角色id
     * @param permissionIdList 权限id列表
     */
    @PutMapping(ResourceConstants.ROLE + "/{roleId}")
    public ResultVO updateRolePermission(@PathVariable String roleId, @RequestParam List<String> permissionIdList){
        roleService.updateRolePermission(roleId,permissionIdList);
        return ResultVOUtil.success();
    }

    /**
     * 分页查询所有角色
     * @param queryPage 分页参数
     * @return
     */
    @GetMapping(ResourceConstants.ROLE)
    public ResultVO listRole(QueryPage queryPage){
        return ResultVOUtil.success(roleService.listRole(queryPage));
    }
}

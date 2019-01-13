package xyz.yazhe.yazheweb.service.user.auth.service;



import com.github.pagehelper.PageInfo;
import xyz.yazhe.yazheweb.service.domain.exception.VerificationException;
import xyz.yazhe.yazheweb.service.domain.user.auth.RO.RoleRO;
import xyz.yazhe.yazheweb.service.domain.user.auth.VO.RoleVO;

import java.util.List;

/**
 * @author BeFondOfTaro
 * Created at 18:59 2018/5/14
 */
public interface RoleService {

    /**
     * 添加角色
     * @param roleRO 角色信息
     */
    void addRole(RoleRO roleRO);

    /**
     * 根据角色id删除角色
     * @param roleId 角色id
     */
    void deleteRoleById(String roleId);

    /**
     * 更新角色的权限
     * @param roleId 角色id
     * @param permissionIdList 权限id列表
     */
    void updateRolePermission(String roleId, List<String> permissionIdList);

    /**
     * 分页查询所有角色
     * @param roleRO
     * @return
     */
    PageInfo<RoleVO> listRole(RoleRO roleRO) throws VerificationException;
}

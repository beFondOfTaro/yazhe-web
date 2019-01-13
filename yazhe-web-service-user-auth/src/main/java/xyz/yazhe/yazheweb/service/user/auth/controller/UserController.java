package xyz.yazhe.yazheweb.service.user.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.yazhe.yazheweb.service.domain.base.ResultVO;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.UserValidatedGroup.AddUser;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.UserValidatedGroup.ListUser;
import xyz.yazhe.yazheweb.service.domain.common.constants.ResourceConstants;
import xyz.yazhe.yazheweb.service.domain.exception.SystemException;
import xyz.yazhe.yazheweb.service.domain.user.auth.RO.UserRO;
import xyz.yazhe.yazheweb.service.user.auth.service.UserService;
import xyz.yazhe.yazheweb.service.util.web.result.ResultVOUtil;

import java.util.List;

/**
 * 用户操作
 * @author BeFondOfTaro
 * Created at 19:21 2018/5/15
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return
     */
    @GetMapping(ResourceConstants.USER + "/{userId}")
    public ResultVO getUserById(@PathVariable("userId") String userId){
        return ResultVOUtil.success(userService.getUserById(userId));
    }

    /**
     * 分页查询所有用户
     * @param userRO 分页参数
     * @return
     */
    @GetMapping(ResourceConstants.USER )
    public ResultVO listUser(@Validated(ListUser.class) UserRO userRO){
        return ResultVOUtil.success(userService.listUser(userRO.getQueryPage()));
    }

    /**
     * 添加用户
     * @param userRO 用户信息
     */
    @PostMapping(ResourceConstants.USER)
    public ResultVO addUser(@Validated(AddUser.class) @RequestBody UserRO userRO) throws SystemException {
        userService.addUser(userRO);
        return ResultVOUtil.success();
    }

    /**
     * 为用户批量更新角色
     * @param userId 用户id
     * @param roleIdList 角色id列表
     */
    @PostMapping(ResourceConstants.USER + "/{userId}/" + ResourceConstants.ROLE)
    public ResultVO updateUserRole(@PathVariable String userId, @RequestBody List<String> roleIdList){
        userService.updateUserRole(userId,roleIdList);
        return ResultVOUtil.success();
    }

    /**
     * 通过用户id删除用户
     * @param userId 用户id
     */
    @DeleteMapping(ResourceConstants.USER + "/{userId}")
    public ResultVO deleteUserById(@PathVariable String userId){
        userService.deleteUserById(userId);
        return ResultVOUtil.success();
    }
}

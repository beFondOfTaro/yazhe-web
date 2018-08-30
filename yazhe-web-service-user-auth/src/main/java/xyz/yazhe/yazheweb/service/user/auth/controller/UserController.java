package xyz.yazhe.yazheweb.service.user.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.yazhe.yazheweb.service.domain.base.QueryPage;
import xyz.yazhe.yazheweb.service.domain.base.ResultVO;
import xyz.yazhe.yazheweb.service.domain.common.constants.ResourceConstants;
import xyz.yazhe.yazheweb.service.domain.user.auth.DTO.UserRegisterDTO;
import xyz.yazhe.yazheweb.service.user.auth.service.UserService;
import xyz.yazhe.yazheweb.service.util.web.result.ResultVOUtil;

import javax.validation.Valid;
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
     * @param queryPage 分页参数
     * @return
     */
    @GetMapping(ResourceConstants.USER )
    public ResultVO listUser(@Valid QueryPage queryPage){
        return ResultVOUtil.success(userService.listUser(queryPage));
    }

    /**
     * 添加用户
     * @param userRegisterDTO 用户信息
     */
    @PostMapping(ResourceConstants.USER)
    public ResultVO addUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO){
        userService.addUser(userRegisterDTO);
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
package xyz.yazhe.yazheweb.service.user.auth.service;



import xyz.yazhe.yazheweb.service.domain.base.QueryPage;
import xyz.yazhe.yazheweb.service.domain.user.auth.VO.UserVO;
import xyz.yazhe.yazheweb.service.domain.user.auth.RO.UserRO;

import java.util.List;

/**
 * @author BeFondOfTaro
 * Created at 19:29 2018/5/13
 */
public interface UserService {

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return
     */
    UserVO getUserById(String userId);

    /**
     * 分页查询所有用户
     * @param queryPage 分页参数
     * @return
     */
    List<UserVO> listUser(QueryPage queryPage);

    /**
     * 根据用户id查询密码(不需要暴露endpoint)
     * @param userId 用户id
     * @return
     */
    String getPasswordByUserId(String userId);

    /**
     * 添加用户
     * @param userRO 用户信息
     */
    void addUser(UserRO userRO);

    /**
     * 为用户批量更新角色
     * @param userId 用户id
     * @param roleIdList 角色id列表
     */
    void updateUserRole(String userId, List<String> roleIdList);

    /**
     * 通过用户id删除用户
     * @param userId 用户id
     */
    void deleteUserById(String userId);

}

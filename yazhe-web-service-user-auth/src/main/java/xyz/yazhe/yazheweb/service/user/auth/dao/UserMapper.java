package xyz.yazhe.yazheweb.service.user.auth.dao;

import org.apache.ibatis.annotations.Param;
import xyz.yazhe.yazheweb.service.domain.user.auth.DO.User;
import xyz.yazhe.yazheweb.service.domain.user.auth.DO.UserAuth;
import xyz.yazhe.yazheweb.service.domain.user.auth.VO.UserVO;

import java.util.Date;
import java.util.List;

/**
 * @author BeFondOfTaro
 * Created at 13:07 2018/5/14
 */
public interface UserMapper {

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return
     */
    UserVO getUserById(@Param("userId") String userId);

    /**
     * 查询所有用户信息
     * @return
     */
    List<UserVO> listUser();


    /**
     * 插入信息到user表
     * @param user 用户信息
     * @return
     */
    int insertUser(User user);

    /**
     * 插入信息到user_auth表
     * @param userAuth
     * @return
     */
    int insertUserAuth(UserAuth userAuth);

    /**
     * 根据用户id删除user表信息
     * @param userId 用户id
     * @return
     */
    int deleteUserById(@Param("userId") String userId);

    /**
     * 根据用户id删除user_auth表信息
     * @param userId 用户id
     * @return
     */
    int deleteUserAuthByUserId(@Param("userId") String userId);

    int updateLastTimeByUserId(@Param("lastTime") Date lastTime, @Param("userId") String userId);

}

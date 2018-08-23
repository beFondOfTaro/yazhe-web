package xyz.yazhe.yazheweb.service.user.auth.dao;

import org.apache.ibatis.annotations.Param;
import xyz.yazhe.yazheweb.service.domain.user.auth.DO.UserAuth;

/**
 * @author BeFondOfTaro
 * Created at 23:22 2018/5/16
 */
public interface UserAuthMapper {

    /**
     * 查询用户身份认证信息
     * @param identifier 标识（手机号 邮箱 用户名或第三方应用的唯一标识）
     * @param credential 登录凭证
     * @param identifyType 登录认证类型,0：用户名密码登录，1：微信登录
     * @return
     */
    UserAuth getUserAuthByLoginInfo(@Param("identifier") String identifier,
									@Param("credential") String credential,
									@Param("identifyType") Integer identifyType);

    /**
     * 根据用户id查询密码
     * @param userId 用户id
     * @return
     */
    String getPasswordByUserId(@Param("userId") String userId);
}

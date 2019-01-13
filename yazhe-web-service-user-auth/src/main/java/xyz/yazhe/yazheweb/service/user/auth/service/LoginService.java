package xyz.yazhe.yazheweb.service.user.auth.service;


import xyz.yazhe.yazheweb.service.domain.exception.UserLoginException;

import java.util.Map;

/**
 * 登录相关服务
 * @author BeFondOfTaro
 * Created at 21:04 2018/5/16
 */
public interface LoginService {

    /**
     * 登录
     * @param identifier
     * @param credential
     * @param identifyType
     * @return 用户通行token
	 * @exception UserLoginException 用户名或密码错误
     */
    Map<String,Object> login(String identifier, String credential, Integer identifyType) throws UserLoginException;

	/**
	 * 注销
	 */
	void logout();
}

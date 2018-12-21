package xyz.yazhe.yazheweb.service.user.auth.service;

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
     */
    Map<String,Object> login(String identifier, String credential, Integer identifyType);

	/**
	 * 注销
	 */
	void logout();
}

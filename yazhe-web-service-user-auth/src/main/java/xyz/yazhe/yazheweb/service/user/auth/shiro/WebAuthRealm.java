package xyz.yazhe.yazheweb.service.user.auth.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.yazhe.yazheweb.service.domain.user.auth.DO.UserAuth;
import xyz.yazhe.yazheweb.service.user.auth.dao.UserAuthMapper;
import xyz.yazhe.yazheweb.service.user.auth.shiro.token.CustomToken;

/**
 * @author Yazhe
 * Created at 17:17 2018/8/24
 */
public class WebAuthRealm extends AuthorizingRealm {

	@Autowired
	private UserAuthMapper userAuthMapper;

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof CustomToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//转换成自定义的token
		CustomToken customToken = (CustomToken)token;
		//从token中获取数据
		String username = customToken.getUsername();
		String password = new String(customToken.getPassword());
		UserAuth userAuth = userAuthMapper.getUserAuthByLoginInfo(username,password,customToken.getIdentifyType());
		if ( userAuth == null){
			throw new AuthenticationException("登录失败，未查询到用户身份信息！");
		}

		return new SimpleAuthenticationInfo(username,userAuth.getCredential(),getName());
	}
}

package xyz.yazhe.yazheweb.service.user.auth.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.yazhe.yazheweb.service.domain.common.constants.CommonConstants;
import xyz.yazhe.yazheweb.service.domain.common.constants.HttpParamKey;
import xyz.yazhe.yazheweb.service.domain.user.auth.DO.User;
import xyz.yazhe.yazheweb.service.domain.user.auth.RO.UserRO;
import xyz.yazhe.yazheweb.service.domain.user.auth.VO.UserVO;
import xyz.yazhe.yazheweb.service.user.auth.dao.UserMapper;
import xyz.yazhe.yazheweb.service.user.auth.service.LoginService;
import xyz.yazhe.yazheweb.service.user.auth.service.UserService;
import xyz.yazhe.yazheweb.service.user.auth.shiro.token.CustomToken;
import xyz.yazhe.yazheweb.service.util.DateUtil;
import xyz.yazhe.yazheweb.service.util.JWTUtil;
import xyz.yazhe.yazheweb.service.util.KeyUtil;
import xyz.yazhe.yazheweb.service.util.web.RequestUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author BeFondOfTaro
 * Created at 21:10 2018/5/16
 */
@Service
public class LoginServiceImpl implements LoginService {

    /**
     * token过期时间(天)
     */
    @Value("${token-expire-time}")
    private int tokenExpireTime;

    @Autowired
	private RedisTemplate<String,String> redisTemplate;
    @Autowired
	private UserMapper userMapper;
    @Autowired
	private UserService userService;

    @Override
    public Map<String, Object> login(String identifier, String credential, Integer identifyType) {
		Subject subject = SecurityUtils.getSubject();
		subject.login(new CustomToken(identifier,credential,System.currentTimeMillis(),identifyType));
		String userId = (String) subject.getPrincipals().getPrimaryPrincipal();
        //根据userId，密码，token过期时间生成token
		String token = JWTUtil.sign(userId, credential, DateUtil.convertDay2Millisecond(tokenExpireTime));
        //存入redis
        redisTemplate.boundValueOps(CommonConstants.RedisKey.AUTH_TOKEN_PREFIX + userId).
				set(token,tokenExpireTime, TimeUnit.DAYS);
        UserVO userVO = userMapper.getUserById(userId);
		userMapper.updateLastTimeByUserId(new Date(),userId);
        //返回给客户端
        Map<String,Object> res = new HashMap<>();
        res.put(HttpParamKey.TOKEN,token);
        res.put("userInfo", userVO);
        return res;
    }

	@Override
	public void logout() {
    	//清除token
		redisTemplate.delete(CommonConstants.RedisKey.AUTH_TOKEN_PREFIX + RequestUtil.getCurrentUserId());
	}

}

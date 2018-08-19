package xyz.yazhe.yazheweb.module.user.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.yazhe.yazheweb.config.exception.CommonException;
import xyz.yazhe.yazheweb.constants.CommonConstants;
import xyz.yazhe.yazheweb.constants.HttpParamKey;
import xyz.yazhe.yazheweb.constants.ResultEnum;
import xyz.yazhe.yazheweb.module.user.auth.POJO.DO.UserAuth;
import xyz.yazhe.yazheweb.module.user.auth.dao.UserAuthMapper;
import xyz.yazhe.yazheweb.module.user.auth.service.LoginService;
import xyz.yazhe.yazheweb.util.DateUtil;
import xyz.yazhe.yazheweb.util.JWTUtil;

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
    private UserAuthMapper userAuthMapper;
    @Autowired
	private RedisTemplate<String,String> redisTemplate;

    @Override
    public Map<String, Object> login(String identifier, String credential, Integer identifyType) {
        UserAuth userAuth = userAuthMapper.getUserAuthByLoginInfo(identifier, credential, identifyType);
        if (null == userAuth){
            throw new CommonException(ResultEnum.LOGIN_FAILED);
        }
        //根据userId，密码，token过期时间生成token
		String token = JWTUtil.sign(userAuth.getUserId(),
				userAuthMapper.getPasswordByUserId(userAuth.getUserId()),
				DateUtil.convertDay2Millisecond(tokenExpireTime));
        //存入redis
        redisTemplate.boundValueOps(CommonConstants.RedisKey.AUTH_TOKEN_PRIFIX + userAuth.getUserId()).
				set(token,tokenExpireTime, TimeUnit.DAYS);
        //返回给客户端
        Map<String,Object> res = new HashMap<>();
        res.put(HttpParamKey.TOKEN,token);
        return res;
    }

}

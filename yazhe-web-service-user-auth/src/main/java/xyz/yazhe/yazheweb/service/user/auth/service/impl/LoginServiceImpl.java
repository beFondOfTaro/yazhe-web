package xyz.yazhe.yazheweb.service.user.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.yazhe.yazheweb.service.domain.common.constants.CommonConstants;
import xyz.yazhe.yazheweb.service.domain.common.constants.HttpParamKey;
import xyz.yazhe.yazheweb.service.domain.common.constants.exception.ResultEnum;
import xyz.yazhe.yazheweb.service.domain.exception.CommonException;
import xyz.yazhe.yazheweb.service.domain.user.auth.DO.User;
import xyz.yazhe.yazheweb.service.domain.user.auth.DO.UserAuth;
import xyz.yazhe.yazheweb.service.domain.user.auth.DTO.UserDTO;
import xyz.yazhe.yazheweb.service.user.auth.dao.UserAuthMapper;
import xyz.yazhe.yazheweb.service.user.auth.dao.UserMapper;
import xyz.yazhe.yazheweb.service.user.auth.service.LoginService;
import xyz.yazhe.yazheweb.service.util.DateUtil;
import xyz.yazhe.yazheweb.service.util.JWTUtil;

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
    private UserAuthMapper userAuthMapper;
    @Autowired
	private RedisTemplate<String,String> redisTemplate;
    @Autowired
	private UserMapper userMapper;

    @Override
    public Map<String, Object> login(String identifier, String credential, Integer identifyType) {
        UserAuth userAuth = userAuthMapper.getUserAuthByLoginInfo(identifier, credential, identifyType);
        if (null == userAuth){
            throw new CommonException(ResultEnum.LOGIN_FAILED);
        }
        String userId = userAuth.getUserId();
        //根据userId，密码，token过期时间生成token
		String token = JWTUtil.sign(userId, credential, DateUtil.convertDay2Millisecond(tokenExpireTime));
        //存入redis
        redisTemplate.boundValueOps(CommonConstants.RedisKey.AUTH_TOKEN_PREFIX + userId).
				set(token,tokenExpireTime, TimeUnit.DAYS);
        UserDTO userDTO = userMapper.getUserById(userId);
		userMapper.updateLastTimeByUserId(new Date(),userId);
        //返回给客户端
        Map<String,Object> res = new HashMap<>();
        res.put(HttpParamKey.TOKEN,token);
        res.put("userInfo",userDTO);
        return res;
    }

}

package xyz.yazhe.yazheweb.service.domain.common.constants;

/**
 * 通用常量类
 * @author BeFondOfTaro
 * Created in 16:36 2018/1/18
 */
public interface CommonConstants {

    /**
     * api版本号
     */
    String API_PREFIX = "/api_v1";

	/**
	 * redis缓存的默认过期时间
	 */
	long REDIS_KEY_DEFAULT_EXPIRE_TIME = 30*24*60*60;

	/**
	 * redis缓存时的键
	 */
	interface RedisKey{
		/**
		 * 业务前缀
		 */
    	String BUSINESS_PREFIX = "dev_man:";

		/**
		 * 用户登录认证token前缀
		 */
		String AUTH_TOKEN_PREFIX = BUSINESS_PREFIX + "token:";

		String USER_PERMISSION_PREFIX = BUSINESS_PREFIX + "user_per:";
	}
}

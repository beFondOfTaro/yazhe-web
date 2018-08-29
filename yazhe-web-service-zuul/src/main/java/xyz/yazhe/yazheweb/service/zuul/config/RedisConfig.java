package xyz.yazhe.yazheweb.service.zuul.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import xyz.yazhe.yazheweb.service.util.spring.config.BaseRedisConfig;

/**
 * @author Yazhe
 * Created at 15:49 2018/8/28
 */
@Configuration
@EnableCaching
public class RedisConfig extends BaseRedisConfig {
}

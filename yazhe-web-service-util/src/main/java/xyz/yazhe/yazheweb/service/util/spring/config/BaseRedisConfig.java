package xyz.yazhe.yazheweb.service.util.spring.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Yazhe
 * Created at 15:41 2018/8/28
 */
public class BaseRedisConfig {

	@Bean
	public RedisTemplate<String,String> stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
		RedisTemplate<String,String> stringRedisTemplate = new RedisTemplate<>();
		stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
		stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
		stringRedisTemplate.setValueSerializer(new StringRedisSerializer());
		stringRedisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		stringRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		return stringRedisTemplate;
	}

	@Bean
	public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		redisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		return redisTemplate;
	}

	@Bean
	public CacheManager cacheManager(RedisTemplate<String,Object> redisTemplate){
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		cacheManager.setDefaultExpiration(30*24*60*60);
		return cacheManager;
	}
}

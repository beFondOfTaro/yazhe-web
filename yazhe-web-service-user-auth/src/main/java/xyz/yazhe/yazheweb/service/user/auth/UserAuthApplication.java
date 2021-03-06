package xyz.yazhe.yazheweb.service.user.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Yazhe
 * Created at 17:36 2018/8/23
 */
@SpringBootApplication
@MapperScan("xyz.yazhe.yazheweb.service.user.auth.dao")
@EnableEurekaClient
public class UserAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAuthApplication.class,args);
	}
}

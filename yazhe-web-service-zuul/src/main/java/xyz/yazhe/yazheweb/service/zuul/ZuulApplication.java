package xyz.yazhe.yazheweb.service.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author Yazhe
 * Created at 12:01 2018/8/24
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableFeignClients
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class,args);
	}
}

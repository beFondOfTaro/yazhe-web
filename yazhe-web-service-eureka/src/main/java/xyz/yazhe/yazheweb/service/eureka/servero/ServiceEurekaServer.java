package xyz.yazhe.yazheweb.service.eureka.servero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Yazhe
 * Created at 9:11 2018/8/24
 */
@SpringBootApplication
@EnableEurekaServer
public class ServiceEurekaServer {
	public static void main(String[] args) {
		SpringApplication.run(ServiceEurekaServer.class,args);
	}
}

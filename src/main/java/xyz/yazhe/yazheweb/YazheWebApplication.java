package xyz.yazhe.yazheweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"xyz.yazhe.yazheweb.module.user.auth.dao"})
public class YazheWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(YazheWebApplication.class, args);
	}
}

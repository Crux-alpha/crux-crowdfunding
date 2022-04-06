package com.crux.crowd.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.FlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableFeignClients
@SpringBootApplication
public class CrowdAuthenticationApplication{

	public static void main(String[] args){
		SpringApplication.run(CrowdAuthenticationApplication.class, args);
	}
}

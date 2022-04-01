package com.crux.crowd.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CrowdAuthenticationApplication{

	public static void main(String[] args){
		SpringApplication.run(CrowdAuthenticationApplication.class, args);
	}
}

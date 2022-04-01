package com.crux.crowd.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class CrowdMainApplication{

	public static void main(String[] args){
		SpringApplication.run(CrowdMainApplication.class, args);
	}
}

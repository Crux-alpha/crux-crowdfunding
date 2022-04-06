package com.crux.crowd.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class CrowdGatewayApplication{

	public static void main(String[] args){
		SpringApplication.run(CrowdGatewayApplication.class, args);
	}
}

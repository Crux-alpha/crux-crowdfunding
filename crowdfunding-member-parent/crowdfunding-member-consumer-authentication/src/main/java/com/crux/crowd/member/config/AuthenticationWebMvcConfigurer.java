package com.crux.crowd.member.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)
public class AuthenticationWebMvcConfigurer implements WebMvcConfigurer{

	@Override
	public void addViewControllers(ViewControllerRegistry registry){
		registry.addViewController("/auth/register").setViewName("register");
	}
}
package com.crux.crowd.member.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)
@Import(BCryptPasswordEncoder.class)
public class AuthenticationWebMvcConfigurer implements WebMvcConfigurer{

	/**
	 * 请求转发处理器
	 * @param registry 处理器注册器
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry){
		// 会员注册页面
		registry.addViewController("/member/register.html").setViewName("register");
		// 会员登录页面
		registry.addViewController("/member/login.html").setViewName("login");
	}
}

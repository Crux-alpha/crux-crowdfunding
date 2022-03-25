package com.crux.crowd.admin.webui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web层服务配置类
 */
@Configuration(proxyBeanMethods = false)
public class AdminWebMvcConfigurer implements WebMvcConfigurer{

	/**
	 * 添加拦截器
	 * @param registry 拦截器注册器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		/* 已弃用
		registry.addInterceptor(new LoginInterceptor())
				.addPathPatterns("/admin/**").excludePathPatterns("/admin/login", "/admin/logout");
		*/
	}
}

package com.crux.crowd.admin.webui.config;

import com.crux.crowd.admin.component.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)
public class AdminWebMvcConfiguration implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new LoginInterceptor())
				.addPathPatterns("/admin/**").excludePathPatterns("/admin/login", "/admin/logout");
	}
}

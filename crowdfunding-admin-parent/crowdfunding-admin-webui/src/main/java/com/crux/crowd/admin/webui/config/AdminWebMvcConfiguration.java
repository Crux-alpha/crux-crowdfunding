package com.crux.crowd.admin.webui.config;

import com.crux.crowd.admin.component.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web层服务配置类
 */
@Configuration(proxyBeanMethods = false)
public class AdminWebMvcConfiguration implements WebMvcConfigurer{

	/**
	 * 添加拦截器
	 * @param registry 拦截器注册器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new LoginInterceptor())
				.addPathPatterns("/admin/**").excludePathPatterns("/admin/login", "/admin/logout");
	}
}

package com.crux.crowd.member.config;

import com.crux.crowd.common.util.CrowdConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		registry.addViewController("/member/register.html").setViewName("/member/register");
		// 会员登录页面
		registry.addViewController("/member/login.html").setViewName("/member/login");
		// 会员中心页面
		registry.addViewController("/member/center.html").setViewName("/member/center");
		// 会员登出页面
		registry.addViewController("/member/logout.html").setViewName("redirect:http://localhost/index.html");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry){
		// 会员登出操作拦截器。将session保存的memberInfo删除
		registry.addInterceptor(new HandlerInterceptor(){
			@Override
			public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
				request.getSession().removeAttribute(CrowdConstant.SESSION_ATTRIBUTE_MEMBER_INFO);
			}
		}).addPathPatterns("/member/logout.html");
	}
}

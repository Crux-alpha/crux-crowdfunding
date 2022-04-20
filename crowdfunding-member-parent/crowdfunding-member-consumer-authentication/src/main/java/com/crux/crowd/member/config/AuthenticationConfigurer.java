package com.crux.crowd.member.config;

import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.member.api.MessageProviderProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Configuration(proxyBeanMethods = false)
@Import(BCryptPasswordEncoder.class)
@EnableConfigurationProperties(MessageProviderProperties.class)
public class AuthenticationConfigurer implements WebMvcConfigurer{

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
			public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull Object handler, ModelAndView modelAndView){
				request.getSession().removeAttribute(CrowdConstant.SESSION_ATTRIBUTE_MEMBER_INFO);
			}
		}).addPathPatterns("/member/logout.html");
		// 登录页面拦截器。如果已经登录，则重定向到个人中心页面
		registry.addInterceptor(new HandlerInterceptor(){
			@Override
			public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception{
				if(Objects.nonNull(request.getSession().getAttribute(CrowdConstant.SESSION_ATTRIBUTE_MEMBER_INFO))){
					response.sendRedirect("http://localhost/member/center.html");
					return false;
				}
				return true;
			}
		}).addPathPatterns("/member/login.html");
	}
}

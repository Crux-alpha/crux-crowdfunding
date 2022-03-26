package com.crux.crowd.admin.webui.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Web应用权限管理配置器
 * @since 2022/03/25
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminWebSecurityConfigurer extends WebSecurityConfigurerAdapter{

	private UserDetailsService userDetailsService;
	private PasswordEncoder passwordEncoder;

	/**
	 * 配置登录账号密码验证行为
	 * @param auth 配置器
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	/**
	 * 配置web资源权限和权限认证行为
	 * @param http 配置器
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests()
				// 静态资源、首页、错误页、登录页无权限
				.antMatchers("/static/**", "/", "/index", "/error/**", "/admin/login").permitAll()
				// 用户管理需要经理角色或超级管理员角色。已使用注解配置代替
				// .antMatchers("/admin/main/user/**").hasAnyRole(ROLE_经理, ROLE_超级管理员)
				// 其他请求需登录
				.anyRequest().authenticated().and()
				// 登录表单设置
				.formLogin().loginPage("/admin/login").defaultSuccessUrl("/admin/main", true)
							.usernameParameter("account").and()
				// 登出设置
				.logout().logoutUrl("/admin/logout").logoutSuccessUrl("/index");
	}

	@Autowired
	public void setUserDetailsService(@Qualifier("userDetailsService") UserDetailsService userDetailsService){
		this.userDetailsService = userDetailsService;
	}

	@Autowired
	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder){
		this.passwordEncoder = passwordEncoder;
	}
}

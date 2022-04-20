package com.crux.crowd.member.config;

import com.crux.crowd.member.api.OSSProviderProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(OSSProviderProperties.class)
public class ProjectConfigurer implements WebMvcConfigurer{

	@Override
	public void addViewControllers(ViewControllerRegistry registry){
		// 我的众筹页面
		registry.addViewController("/start.html").setViewName("/project/start");
	}
}

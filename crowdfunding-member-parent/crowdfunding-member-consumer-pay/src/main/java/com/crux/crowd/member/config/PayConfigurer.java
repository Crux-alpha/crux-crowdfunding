package com.crux.crowd.member.config;

import com.crux.crowd.member.api.AlipayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(AlipayProperties.class)
public class PayConfigurer{
}

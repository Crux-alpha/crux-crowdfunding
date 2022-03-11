package com.crux.crowd.admin.component;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Admin组件自动配置类
 * 默认加载component下所有组件
 * 开启了声明式事务
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan("com.crux.crowd.admin.component")
@MapperScan("com.crux.crowd.admin.component.mapper")
@AutoConfigureAfter(MybatisPlusAutoConfiguration.class)
public class AdminComponentAutoConfiguration{

	@Configuration
	@EnableTransactionManagement
	static class ServiceTransactionConfiguration{

		@Bean
		@ConditionalOnMissingBean(PlatformTransactionManager.class)
		public DataSourceTransactionManager transactionManager(DataSource dataSource){
			return new DataSourceTransactionManager(dataSource);
		}
	}
}

package com.crux.crowd.admin.component;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Admin组件自动配置类。已经配置了
 * <ul>
 *     <li>加载component下所有组件</li>
 *     <li>开启声明式事务</li>
 *     <li>分页插件</li>
 * </ul>
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan("com.crux.crowd.admin.component")
@MapperScan("com.crux.crowd.admin.component.mapper")
@EnableConfigurationProperties(AdminComponentProperties.class)
@AutoConfigureAfter(MybatisPlusAutoConfiguration.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AdminComponentAutoConfiguration{

	@Bean
	@ConditionalOnMissingBean(MybatisPlusInterceptor.class)
	public MybatisPlusInterceptor mybatisPlusInterceptor(AdminComponentProperties componentProperties){
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		if(componentProperties.isPaginationInnerInterceptorEnabled()){
			interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		}
		if(componentProperties.isBlockAttackInnerInterceptorEnabled()){
			interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
		}

		return interceptor;
	}

	@Bean
	public ConfigurationCustomizer configurationCustomizer(){
		return configuration -> configuration.setUseGeneratedShortKey(false);
	}

	@Configuration(proxyBeanMethods = false)
	@EnableTransactionManagement
	static class ServiceTransactionConfiguration{

		@Bean
		@ConditionalOnMissingBean(PlatformTransactionManager.class)
		public DataSourceTransactionManager transactionManager(DataSource dataSource){
			return new DataSourceTransactionManager(dataSource);
		}
	}
}

package com.crux.crowd.admin.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * component模块配置类
 */
@ConfigurationProperties("crowd.admin.component")
@Setter
@Getter
public class AdminComponentProperties{

	/**
	 * 是否开启分页插件。默认开启
	 */
	private boolean paginationInnerInterceptorEnabled = true;
	/**
	 * 是否开启防止全表更新插件
	 */
	private boolean blockAttackInnerInterceptorEnabled;
}

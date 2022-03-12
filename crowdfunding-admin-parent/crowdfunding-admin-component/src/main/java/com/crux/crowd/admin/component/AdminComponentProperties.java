package com.crux.crowd.admin.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("crowd.admin.component")
@Setter
@Getter
public class AdminComponentProperties{

	private boolean paginationInnerInterceptorEnabled = true;
	private boolean blockAttackInnerInterceptorEnabled;
}

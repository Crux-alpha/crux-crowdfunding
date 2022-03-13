package com.crux.crowd.admin.component.service;

import com.crux.crowd.common.util.CrowdConstant;

/**
 * 当未登录状态访问受保护的资源时，抛出此异常表示拒绝访问
 * @since 2022/03/11
 */
public class AccessDeniedException extends ServiceException{

	public AccessDeniedException(){
		super(CrowdConstant.TipsMessage.ACCESS_FORBIDDEN);
	}

	public AccessDeniedException(String message){
		super(message);
	}

	public AccessDeniedException(String message, Throwable cause){
		super(message, cause);
	}
}

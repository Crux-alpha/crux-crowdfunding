package com.crux.crowd.admin.component.service;

import com.crux.crowd.common.util.CrowdConstant;

/**
 * 登录失败异常
 * @since 2022/03/11
 */
public class LoginFailedException extends ServiceException{

	private static final long serialVersionUID = 1L;

	public LoginFailedException(){
		super(CrowdConstant.TipsMessage.LOGIN_FAILED);
	}

	public LoginFailedException(String message){
		super(message);
	}

	public LoginFailedException(String message, Throwable cause){
		super(message, cause);
	}
}

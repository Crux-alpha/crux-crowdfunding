package com.crux.crowd.member.service;

import com.crux.crowd.common.util.CrowdConstant;

/**
 * 账号重复异常
 */
public class LoginAccountRepeatedException extends ServiceException{

	public LoginAccountRepeatedException(){
		super(CrowdConstant.TipsMessage.ACCOUNT_IN_USE);
	}

	public LoginAccountRepeatedException(String message){
		super(message);
	}

	public LoginAccountRepeatedException(String message, Throwable cause){
		super(message, cause);
	}
}

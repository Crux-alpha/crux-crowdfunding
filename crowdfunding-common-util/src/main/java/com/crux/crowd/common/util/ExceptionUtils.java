package com.crux.crowd.common.util;

import com.crux.crowd.common.LoginFailedException;

public final class ExceptionUtils{

	private ExceptionUtils(){}

	public static LoginFailedException loginFailed(String message){
		return new LoginFailedException(message);
	}

	public static LoginFailedException loginFailed(){
		return loginFailed(CrowdConstant.TipsMessage.LOGIN_FAILED);
	}
}

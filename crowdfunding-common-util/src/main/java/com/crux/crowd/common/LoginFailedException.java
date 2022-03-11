package com.crux.crowd.common;

/**
 * 登录失败异常
 * @since 2022/03/11
 */
public class LoginFailedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public LoginFailedException(){
		super();
	}

	public LoginFailedException(String message){
		super(message);
	}

	public LoginFailedException(String message, Throwable cause){
		super(message, cause);
	}
}

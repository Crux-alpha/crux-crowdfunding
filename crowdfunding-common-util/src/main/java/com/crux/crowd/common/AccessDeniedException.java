package com.crux.crowd.common;

/**
 * 当未登录状态访问受保护的资源时，抛出此异常表示拒绝访问
 * @since 2022/03/11
 */
public class AccessDeniedException extends RuntimeException{
	public AccessDeniedException(){
		super();
	}

	public AccessDeniedException(String message){
		super(message);
	}

	public AccessDeniedException(String message, Throwable cause){
		super(message, cause);
	}
}

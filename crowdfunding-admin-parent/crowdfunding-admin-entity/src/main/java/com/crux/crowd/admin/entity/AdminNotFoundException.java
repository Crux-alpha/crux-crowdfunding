package com.crux.crowd.admin.entity;

/**
 * 找不到Admin异常
 */
public class AdminNotFoundException extends AdminException{

	public AdminNotFoundException(){
		super();
	}

	public AdminNotFoundException(String message){
		super(message);
	}

	public AdminNotFoundException(String message, Throwable cause){
		super(message, cause);
	}
}

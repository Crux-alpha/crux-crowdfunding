package com.crux.crowd.admin.component.service;

import com.crux.crowd.common.util.CrowdConstant;

/**
 * 找不到Admin异常
 */
public class AdminNotFoundException extends ServiceException{

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

package com.crux.crowd.member.service;

import com.crux.crowd.common.util.CrowdConstant;

public class PhoneRepeatedException extends ServiceException{

	public PhoneRepeatedException(){
		super(CrowdConstant.TipsMessage.PHONE_IN_USE);
	}

	public PhoneRepeatedException(String message){
		super(message);
	}

	public PhoneRepeatedException(String message, Throwable cause){
		super(message, cause);
	}
}

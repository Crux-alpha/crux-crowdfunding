package com.crux.crowd.admin.entity;

public class AdminException extends RuntimeException{

	public AdminException(){
		super();
	}

	public AdminException(String message){
		super(message);
	}

	public AdminException(String message, Throwable cause){
		super(message, cause);
	}
}

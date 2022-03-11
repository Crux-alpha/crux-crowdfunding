package com.crux.crowd.common.util;

/**
 * 响应状态
 */
public enum ResponseResult{
	SUCCESS("成功"), FAILURE("失败"), UNKNOWN("未知"), ERROR("错误");

	private final String result;

	ResponseResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}
}

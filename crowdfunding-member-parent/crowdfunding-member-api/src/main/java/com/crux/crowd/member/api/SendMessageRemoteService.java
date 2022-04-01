package com.crux.crowd.member.api;

import com.crux.crowd.common.util.ResultEntity;

/**
 * 发送短信验证码远程服务
 * 使用第三方API
 */
public interface SendMessageRemoteService{

	/**
	 * 使用随机生成的验证码发送短信
	 * 验证码由6位随机数字组成
	 * @param phone 手机号
	 * @param minutes 超时时长
	 * @return 执行结果
	 */
	default ResultEntity<String,String> sendMessage(String phone, int minutes){
		String code;

		do{
			code = Double.toString(Math.random()).substring(2);
		}while(code.length() < 6);

		code = code.substring(0, 6);

		return sendMessage(phone, code, minutes);
	}

	/**
	 * 使用给定验证码发送短信
	 * @param phone 手机号
	 * @param code 验证码
	 * @param minutes 超时时长
	 * @return 执行结果
	 */
	ResultEntity<String,String> sendMessage(String phone, String code, int minutes);

}

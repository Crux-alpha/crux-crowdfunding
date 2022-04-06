package com.crux.crowd.member.api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties("crowd.message-api")
public class MessageProviderProperties{
	/**
	 * API URL
	 */
	private String host;
	/**
	 * API URI
	 */
	private String path;
	/**
	 * 请求方式
	 */
	private String method;
	/**
	 * 请求头信息
	 */
	private Map<String,String> headers;
	/**
	 * 签名信息
	 */
	private Map<String,String> sign;
	/**
	 * 参数信息
	 */
	private Param param;
	/**
	 * 模板信息
	 */
	private Map<String,String> template;
	/**
	 * 手机号参数名
	 */
	private String mobileName;
	/**
	 * 请求体信息
	 */
	private Map<String,String> bodies;

	@Data
	public static class Param{
		/**
		 * 编辑验证码，有效时长的参数名
		 */
		private String paramName;
		/**
		 * 验证码前缀
		 */
		private String codePrefix;
		/**
		 * 有效时长前缀
		 */
		private String minutePrefix;
	}
}



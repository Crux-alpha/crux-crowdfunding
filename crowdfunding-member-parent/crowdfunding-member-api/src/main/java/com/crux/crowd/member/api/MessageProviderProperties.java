package com.crux.crowd.member.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Optional;

@Setter
@Getter
@ConfigurationProperties("crowd.message-api")
public class MessageProviderProperties{

	public static final String CODE_PATTERN = "#{code}";
	public static final String MINUTE_PATTERN = "#{minute}";
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
	 * 其中key为短信模板参数名，value为模板表达式；
	 * 例如code:#{code},code=3 ==> code:3
	 */
	private Map<String,String> param;
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

	public void setParam(Map<String,String> param){
		this.param = Optional.ofNullable(param).filter(p -> p.values().stream().allMatch(v -> v.contains(CODE_PATTERN)))
				.orElseThrow(() -> new IllegalArgumentException("参数缺少必要的表达式：" + CODE_PATTERN));
	}
}



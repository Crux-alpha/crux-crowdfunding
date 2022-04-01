package com.crux.crowd.common.util;

import java.util.Collections;
import java.util.Map;

/**
 * 项目统一的响应实体类
 * @param <K> 键的类型
 * @param <V> 值的类型
 */
@Deprecated
public class ResponseMessage<K,V>{
	/**
	 * 响应状态
	 */
	private ResponseResult result;
	/**
	 * 响应信息
	 */
	private String message;
	/**
	 * 响应数据
	 */
	private Map<K,V> data;

	private ResponseMessage(){}

	ResponseMessage(ResponseResult result, String message, Map<K,V> data){
		this.result = result;
		this.message = message;
		this.data = data;
	}


	public ResponseResult getResult(){
		return result;
	}

	public String getMessage(){
		return message;
	}

	public Map<K,V> getData(){
		return data;
	}

	public static <K,V> ResponseMessage<K,V> success(String message, Map<K,V> data){
		return new ResponseMessage<>(ResponseResult.SUCCESS, message, data);
	}

	public static ResponseMessage<?,?> success(String message){
		return success(message, Collections.emptyMap());
	}

	public static ResponseMessage<?,?> success(){
		return success(Collections.emptyMap());
	}

	public static <K,V> ResponseMessage<K,V> success(Map<K,V> data){
		return success(null, data);
	}

	public static <K,V> ResponseMessage<K,V> failure(String message, Map<K,V> data){
		return new ResponseMessage<>(ResponseResult.FAILURE, message, data);
	}

	public static ResponseMessage<?,?> failure(String message){
		return failure(message, Collections.emptyMap());
	}

	public static ResponseMessage<?,?> failure(){
		return failure(Collections.emptyMap());
	}

	public static <K,V> ResponseMessage<K,V> failure(Map<K,V> data){
		return failure(null, data);
	}
}

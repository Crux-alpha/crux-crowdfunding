package com.crux.crowd.common.util;

/**
 * 项目统一的响应实体类
 * @param <T> 响应数据的类型
 */
public class ResponseMessage<T>{
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
	private T data;

	private ResponseMessage(){}

	ResponseMessage(ResponseResult result, String message, T data){
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

	public T getData(){
		return data;
	}

	public static <T> ResponseMessage<T> success(String message, T data){
		return new ResponseMessage<>(ResponseResult.SUCCESS, message, data);
	}

	public static ResponseMessage<?> success(String message){
		return success(message, null);
	}

	public static ResponseMessage<?> success(){
		return success(null);
	}

	public static <T> ResponseMessage<T> success(T data){
		return success(null, data);
	}

	public static <T> ResponseMessage<T> failure(String message, T data){
		return new ResponseMessage<>(ResponseResult.FAILURE, message, data);
	}

	public static ResponseMessage<?> failure(String message){
		return failure(message, null);
	}

	public static ResponseMessage<?> failure(){
		return failure(null);
	}

	public static <T> ResponseMessage<T> failure(T data){
		return failure(null, data);
	}
}

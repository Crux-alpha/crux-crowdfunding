package com.crux.crowd.common.util;

import java.util.Collections;
import java.util.Map;

public class ResultEntity<K,V>{

	private ResponseResult result;
	private String message;
	private Map<K,V> data;

	private ResultEntity(){}

	ResultEntity(ResponseResult result, String message, Map<K,V> data){
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

	public static <K,V> ResultEntity<K,V> success(String message, Map<K,V> data){
		return new ResultEntity<>(ResponseResult.SUCCESS, message, data);
	}

	public static <K,V> ResultEntity<K,V> success(String message){
		return success(message, Collections.emptyMap());
	}

	public static <K,V> ResultEntity<K,V> success(){
		return success(Collections.emptyMap());
	}

	public static <K,V> ResultEntity<K,V> success(Map<K,V> data){
		return success(null, data);
	}

	public static <K,V> ResultEntity<K,V> failure(String message, Map<K,V> data){
		return new ResultEntity<>(ResponseResult.FAILURE, message, data);
	}

	public static <K,V> ResultEntity<K,V> failure(String message){
		return failure(message, Collections.emptyMap());
	}

	public static <K,V> ResultEntity<K,V> failure(){
		return failure(Collections.emptyMap());
	}

	public static <K,V> ResultEntity<K,V> failure(Map<K,V> data){
		return failure(null, data);
	}

	public static <K,V> ResultEntity<K,V> error(String message){
		return error(message, null);
	}

	public static <K,V> ResultEntity<K,V> error(String message, Map<K,V> data){
		return new ResultEntity<>(ResponseResult.ERROR, message, data);
	}

	@Override
	public String toString(){
		return "执行结果{" +
				"result=" + result.getResult() +
				", message=" + message +
				", data=" + data +
				'}';
	}
}

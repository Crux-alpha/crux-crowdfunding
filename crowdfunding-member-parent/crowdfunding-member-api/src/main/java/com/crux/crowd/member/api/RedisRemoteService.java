package com.crux.crowd.member.api;

import com.crux.crowd.common.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

/**
 * redis远程服务
 */
@FeignClient("crowd-redis")
public interface RedisRemoteService{

	/**
	 * set 'key' 'value'
	 * @param timeout 超时时长。默认-1
	 * @param timeunit 时间单位。默认毫秒
	 * @return 执行结果
	 */
	@RequestMapping("/set")
	ResultEntity<?,?> set(String key, String value,
						  @RequestParam(name = "timeout", required = false, defaultValue = "-1") long timeout,
						  @RequestParam(name = "timeunit", required = false, defaultValue = "MILLISECONDS") TimeUnit timeunit);

	/**
	 * get 'key'
	 * @return value
	 */
	@RequestMapping("/get")
	ResultEntity<String,String> get(String key);

	/**
	 * del 'key'
	 * @return 执行结果
	 */
	@RequestMapping("/delete")
	ResultEntity<?,?> delete(String key);
}

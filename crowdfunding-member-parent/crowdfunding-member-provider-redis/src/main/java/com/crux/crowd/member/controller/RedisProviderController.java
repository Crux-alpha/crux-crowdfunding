package com.crux.crowd.member.controller;

import com.crux.crowd.common.util.ResultEntity;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static java.util.Collections.singletonMap;

import java.time.Duration;
import java.util.Optional;

@RestController
public class RedisProviderController{

	private final StringRedisTemplate redisTemplate;

	public RedisProviderController(StringRedisTemplate redisTemplate){this.redisTemplate = redisTemplate;}

	/**
	 * set 'key' 'value'
	 * @param timeout 超时时长
	 * @return 执行结果
	 */
	@PostMapping("/set")
	ResultEntity<?,?> set(@RequestParam("key") String key,
						  @RequestParam("value") String value,
						  @RequestParam(name = "expire", required = false) Duration timeout){

		if(timeout == null || timeout.isNegative()) valueOperations().set(key, value);

		else valueOperations().set(key, value, timeout);

		return ResultEntity.success("保存成功");
	}

	/**
	 * get 'key'
	 * @return value
	 */
	@GetMapping("/get")
	ResultEntity<String,String> get(@RequestParam("key") String key){
		String value = valueOperations().get(key);
		if(Optional.ofNullable(value).filter(StringUtils::hasLength).isPresent()){
			return ResultEntity.success(singletonMap(key, value));
		}
		return ResultEntity.failure("没有查询到数据");
	}

	/**
	 * del 'key'
	 * @return 执行结果
	 */
	@DeleteMapping("/delete")
	ResultEntity<?,?> delete(@RequestParam("key") String key){
		Boolean result = redisTemplate.delete(key);
		return Boolean.TRUE.equals(result) ? ResultEntity.success("删除成功") : ResultEntity.failure("删除失败");
	}


	private ValueOperations<String,String> valueOperations(){
		return redisTemplate.opsForValue();
	}
}

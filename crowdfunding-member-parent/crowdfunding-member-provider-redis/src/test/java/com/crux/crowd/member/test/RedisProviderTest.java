package com.crux.crowd.member.test;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class RedisProviderTest{

	@Autowired
	StringRedisTemplate redisTemplate;

	private static final Logger log = LoggerFactory.getLogger(RedisProviderTest.class);

	@Test
	void redisConnection(){
		log.info("测试redis连接...");
		log.info("keys *");
		Set<String> keys = redisTemplate.keys("*");
		keys.forEach(log::info);
	}

}

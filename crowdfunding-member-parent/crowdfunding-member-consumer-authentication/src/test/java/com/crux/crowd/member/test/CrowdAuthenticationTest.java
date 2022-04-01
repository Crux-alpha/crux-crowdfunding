package com.crux.crowd.member.test;

import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.api.SendMessageRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class CrowdAuthenticationTest{

	@Autowired
	SendMessageRemoteService messageRemoteService;

	@Test
	@DisplayName("短信API-发送验证码")
	void sendMessage(){
		ResultEntity<String,String> result = messageRemoteService.sendMessage("15218948903", 3);
		log.info(result.toString());
	}
}

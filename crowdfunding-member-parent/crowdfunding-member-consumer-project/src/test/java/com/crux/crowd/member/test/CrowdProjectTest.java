package com.crux.crowd.member.test;

import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.api.AliyunOSSRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@SpringBootTest
public class CrowdProjectTest{

	@Autowired
	AliyunOSSRemoteService ossRemoteService;

	@Test
	@DisplayName("OSS服务-upload()")
	void AliyunOSSRemoteServiceUpload() throws IOException{
		FileInputStream inputStream = new FileInputStream("src/test/java/com/crux/crowd/member/test/oss-upload-test.txt");
		ResultEntity<String,String> result = ossRemoteService.upload("oss-upload-test.txt", inputStream);
		log.info(result.toString());

	}
}

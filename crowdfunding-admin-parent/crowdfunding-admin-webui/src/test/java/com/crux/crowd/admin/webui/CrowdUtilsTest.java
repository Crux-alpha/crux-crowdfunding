package com.crux.crowd.admin.webui;

import com.crux.crowd.common.util.CrowdUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

@Slf4j
class CrowdUtilsTest{

	@Test
	@DisplayName("CrowdUtils")
	void testMD5Encryption(){
		String str = "SAIERHAO123";
		String encoded = CrowdUtils.md5Encryption(str);
		log.info("\"{}\" 加密后是 \"{}\"", str, encoded);
	}

	@Test
	@DisplayName("DigestUtils")
	void testMD5Utils(){
		String str = "SAIERHAO123";
		String encoded = DigestUtils.md5DigestAsHex(str.getBytes());
		log.info("\"{}\" 加密后是 \"{}\"", str, encoded);
	}

	@Test
	void testCode(){
		System.out.println("0123456789".substring(0, 5));
	}
}

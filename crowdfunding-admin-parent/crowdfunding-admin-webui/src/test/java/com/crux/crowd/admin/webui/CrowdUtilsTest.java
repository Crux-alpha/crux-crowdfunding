package com.crux.crowd.admin.webui;

import com.crux.crowd.common.util.CrowdUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class CrowdUtilsTest{

	@Test
	void testMD5Encryption(){
		String str = "SAIERHAO123";
		String encoded = CrowdUtils.md5Encryption(str);
		log.info("\"{}\" 加密后是 \"{}\"", str, encoded);
	}
}

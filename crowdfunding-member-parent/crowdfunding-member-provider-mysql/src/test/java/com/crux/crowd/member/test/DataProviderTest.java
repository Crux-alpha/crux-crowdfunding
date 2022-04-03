package com.crux.crowd.member.test;

import com.crux.crowd.member.entity.po.MemberPO;
import com.crux.crowd.member.mapper.MemberPOMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@SpringBootTest
class DataProviderTest{

	@Autowired
	DataSource dataSource;
	@Autowired
	MemberPOMapper memberMapper;
	@Autowired(required = false)
	BCryptPasswordEncoder passwordEncoder;

	private static final Logger log = LoggerFactory.getLogger(DataProviderTest.class);

	@Test
	void dataSourceConnection(){
		log.info("测试数据源...");
		log.debug(dataSource.toString());
	}

	@Test
	@Disabled
	void memberMapper(){
		MemberPO member = new MemberPO("jack", passwordEncoder.encode("123456"), "12345678900");
		memberMapper.insert(member);
		log.debug(member.toString());
	}
}

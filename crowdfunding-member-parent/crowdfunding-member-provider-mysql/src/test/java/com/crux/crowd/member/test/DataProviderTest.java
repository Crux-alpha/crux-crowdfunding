package com.crux.crowd.member.test;

import com.crux.crowd.member.entity.po.MemberPO;
import com.crux.crowd.member.entity.po.ReturnPO;
import com.crux.crowd.member.entity.vo.MemberConfirmInfoVO;
import com.crux.crowd.member.entity.vo.MemberLaunchInfoVO;
import com.crux.crowd.member.entity.vo.ProjectVO;
import com.crux.crowd.member.entity.vo.ReturnVO;
import com.crux.crowd.member.mapper.MemberPOMapper;
import com.crux.crowd.member.service.ProjectService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

@SpringBootTest
class DataProviderTest{

	@Autowired
	DataSource dataSource;
	@Autowired
	MemberPOMapper memberMapper;
	@Autowired(required = false)
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	ProjectService projectService;

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

	@Test
	@Disabled
	void saveProject(){
		ProjectVO projectVO = new ProjectVO(
				Arrays.asList(1, 2, 3),
				Arrays.asList(1, 4, 9),
				"测试项目",
				"简单介绍",
				BigDecimal.valueOf(666.0),
				30,
				"头图路径",
				Arrays.asList("详情图片01", "详情图片02", "详情图片03"),
				new MemberLaunchInfoVO("简单介绍", "详细介绍", "12345678901", "3216549870"),
				Collections.singletonList(new ReturnVO(ReturnPO.ReturnType.PRACTICAL, BigDecimal.valueOf(233.0), "回报内容", 0, false, 0, BigDecimal.valueOf(5.5), false, 60, "说明图片")),
				new MemberConfirmInfoVO("12345678901", "123456789012345678")
		);
		projectService.saveProject(projectVO, 666);
	}
}

package com.crux.crowd.admin.webui;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.crux.crowd.admin.component.service.AdminService;
import com.crux.crowd.admin.entity.Admin;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
@Slf4j
class CrowdfundingAdminWebuiApplicationTests{

	@Autowired
	AdminService adminService;

	@Test
	void contextLoads(){
		Logger logger = LoggerFactory.getLogger(CrowdfundingAdminWebuiApplicationTests.class);
		logger.debug("debug");
		logger.info("info");
		logger.warn("warn");
		logger.error("error");
	}

	@Test
	void insert(){
		Admin admin = new Admin("jack", "jack", "jack123", "jack@sina.com");
		log.info("保存{}, 你的信息：{}", adminService.save(admin), admin);
	}

	@Test
	void select(){
		Admin admin = adminService.getOne(Wrappers.lambdaQuery(Admin.class).eq(Admin::getUserName, "admin"));
		log.info(admin.toString());
	}

	@Test
	void transaction(){
		boolean flag = adminService.saveBatch(Arrays.asList(new Admin("test", "555555", "test", "test@126.com"),
				new Admin(4, "jerry", "123456", "jerry", "jerry@126.com", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")));
		log.info("{}", flag);
	}
}

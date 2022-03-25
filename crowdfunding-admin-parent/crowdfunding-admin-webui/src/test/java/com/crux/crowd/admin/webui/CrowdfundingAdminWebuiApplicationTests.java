package com.crux.crowd.admin.webui;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crux.crowd.admin.component.service.AdminService;
import com.crux.crowd.admin.component.service.RoleService;
import com.crux.crowd.admin.entity.Admin;
import com.crux.crowd.admin.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Slf4j
class CrowdfundingAdminWebuiApplicationTests{

	@Autowired
	AdminService adminService;

	@Autowired
	RoleService roleService;

	@Test
	void contextLoads(){
		Logger logger = LoggerFactory.getLogger(CrowdfundingAdminWebuiApplicationTests.class);
		logger.debug("debug");
		logger.info("info");
		logger.warn("warn");
		logger.error("error");
	}

	@Test
	@Disabled
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

	@Test
	void insert255Admin(){
		List<Admin> list = new ArrayList<>();
		for(int i=0; i<255; i++){
			String uuid = UUID.randomUUID().toString();
			String loginAcct = uuid.substring(0, 10);
			String userPswd = DigestUtils.md5DigestAsHex(loginAcct.getBytes());
			String email = loginAcct + "@163.com";
			Admin admin = new Admin(loginAcct, loginAcct, userPswd, email);
			list.add(admin);
		}
		log.info("保存{}", adminService.saveBatch(list) ? "成功" : "失败");
	}

	@Test
	void insert255Role(){
		List<Role> list = new ArrayList<>();
		for(int i=0; i<255; i++){
			String uuid = UUID.randomUUID().toString();
			String name = uuid.substring(0, 10);
			Role role = new Role(name);
			list.add(role);
		}
		log.info("保存{}", roleService.saveBatch(list) ? "成功" : "失败");
	}

	@Test
	void save(){
		boolean save = adminService.save(new Admin("admin", "crux_alpha", "SAIERHAO123", "1992980352@qq.com"));
		log.info("{}", save);
	}

	@Test
	void selectRolesAssignedByAdminId(){
		List<Role> rolesAssigned = roleService.getRolesAssigned(1, false);
		log.info("1已分配角色：{}", rolesAssigned.toString());
	}

	@Test
	void updateRolesAssignedByAdminId(){
		boolean result = adminService.updateRolesAssigned(1, Arrays.asList(196,197,198,199,200));
		log.info("保存{}", result);
	}

	@Test
	void passwordEncoded(){
		List<Admin> adminList = adminService.list();
		adminService.removeBatchByIds(adminList);
		Admin crux = adminList.remove(0);
		crux.setUserPswd("SAIERHAO123");
		adminList.forEach(admin -> admin.setUserPswd("123456"));
		adminList.add(crux);
		adminService.saveBatch(adminList);
		adminService.list().forEach(admin -> log.info(admin.toString()));
	}
}

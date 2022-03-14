package com.crux.crowd.admin.component.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 转发视图处理器。仅仅为了转发视图
 */
@Controller
public class ForwardViewController{

	@RequestMapping({"/", "/index"})
	public String index(){
		return "index";
	}

	@RequestMapping("/admin/login")
	public String adminLogin(){
		return "admin-login";
	}

	@RequestMapping("/admin/main")
	public String adminMain(){
		return "admin/main";
	}

	@RequestMapping("/admin/main/user")
	public String adminMainUser(){
		return "admin/main-user";
	}

	@RequestMapping("/admin/main/role")
	public String adminMainRole(){
		return "admin/role";
	}
}

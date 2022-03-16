package com.crux.crowd.admin.component.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 转发视图处理器。仅仅为了转发视图
 */
@Controller
public class ForwardViewController{
	/**
	 * 首页
	 */
	@RequestMapping({"/", "/index"})
	public String index(){
		return "index";
	}

	/**
	 * 后台登录
	 */
	@RequestMapping("/admin/login")
	public String adminLogin(){
		return "admin-login";
	}

	/**
	 * 后台管理-主页
	 */
	@RequestMapping("/admin/main")
	public String adminMain(){
		return "admin/main";
	}

	/**
	 * 后台管理-用户维护
	 */
	@RequestMapping("/admin/main/user")
	public String adminMainUser(){
		return "admin/main-user";
	}

	/**
	 * 后台管理-角色维护
	 */
	@RequestMapping("/admin/main/role")
	public String adminMainRole(){
		return "admin/role";
	}

	/**
	 * 后台管理-菜单维护
	 */
	@RequestMapping("/admin/main/menu")
	public String adminMainMenu(){
		return "admin/permission";
	}
}

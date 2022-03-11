package com.crux.crowd.admin.component.controller;

import com.crux.crowd.admin.component.service.AdminService;
import com.crux.crowd.admin.entity.Admin;
import com.crux.crowd.common.util.CrowdConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController{

	private final AdminService service;

	public AdminController(AdminService service){
		this.service = service;
	}

	@GetMapping("/login")
	public String toLogin(){
		return "admin-login";
	}

	@PostMapping("/login")
	public String login(String account, String password, HttpSession session){
		final Admin admin = service.login(account, password);
		session.setAttribute(CrowdConstant.ADMIN_LOGIN_ACCOUNT, admin);
		return "redirect:/admin/main";
	}

	@RequestMapping("/main")
	public String main(){
		return "main";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/index";
	}

}

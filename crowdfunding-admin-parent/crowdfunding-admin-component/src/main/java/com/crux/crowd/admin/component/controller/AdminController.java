package com.crux.crowd.admin.component.controller;

import com.crux.crowd.admin.component.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController{

	private final AdminService service;

	public AdminController(AdminService service){
		this.service = service;
	}

	@RequestMapping("/login")
	public String toLogin(){
		return "admin-login";
	}

}

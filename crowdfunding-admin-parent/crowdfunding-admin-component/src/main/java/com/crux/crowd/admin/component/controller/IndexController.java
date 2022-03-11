package com.crux.crowd.admin.component.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController{

	@RequestMapping({"/", "/index"})
	public String index(){
		return "index";
	}
}

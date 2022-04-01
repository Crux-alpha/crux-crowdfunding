package com.crux.crowd.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewForwardController{

	@RequestMapping({"/", "/index"})
	public String index(){
		/* -----加载首页数据----- */
		return "index";
	}
}

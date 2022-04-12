package com.crux.crowd.member.controller;

import com.crux.crowd.member.api.DataSourceRemoteService;
import com.crux.crowd.member.entity.vo.PortalTypeVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController{

	private final DataSourceRemoteService dataSourceRemoteService;

	public IndexController(DataSourceRemoteService dataSourceRemoteService){
		this.dataSourceRemoteService = dataSourceRemoteService;
	}

	@RequestMapping({"/", "/index.html"})
	public String index(Model model){
		/* -----加载首页数据----- */
		Map<String,List<PortalTypeVO>> data = dataSourceRemoteService.getPortalProjectAll().getData();
		data.forEach(model::addAttribute);

		return "index";
	}
}

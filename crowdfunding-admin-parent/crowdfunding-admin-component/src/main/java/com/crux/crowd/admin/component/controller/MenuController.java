package com.crux.crowd.admin.component.controller;

import com.crux.crowd.admin.component.service.MenuService;
import com.crux.crowd.admin.entity.Menu;
import com.crux.crowd.common.util.ResponseMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * 菜单维护处理器
 */
@RestController
@RequestMapping("/admin/main/menu")
public class MenuController{

	private final MenuService menuService;

	public MenuController(MenuService menuService){
		this.menuService = menuService;
	}

	/**
	 * 获取菜单树型结构
	 * @return 根节点
	 */
	@RequestMapping("/whole_tree")
	public ResponseMessage<String,Menu> getWholeTree(){
		Menu root = menuService.getRootMenu();
		return ResponseMessage.success(Collections.singletonMap("root", root));
	}
}

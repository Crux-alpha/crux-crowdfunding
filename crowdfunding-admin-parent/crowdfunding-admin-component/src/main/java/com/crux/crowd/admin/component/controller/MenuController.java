package com.crux.crowd.admin.component.controller;

import com.crux.crowd.admin.component.service.MenuService;
import com.crux.crowd.admin.entity.Menu;
import com.crux.crowd.common.util.ResponseMessage;
import org.springframework.web.bind.annotation.*;

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
	@GetMapping("/whole_tree")
	public ResponseMessage<String,Menu> getWholeTree(){
		Menu root = menuService.getRootMenu();
		return ResponseMessage.success(Collections.singletonMap("root", root));
	}

	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	public ResponseMessage<?,?> saveOrUpdateNode(Menu menu){
		menuService.saveOrUpdate(menu);
		return ResponseMessage.success("保存成功！");
	}

	@DeleteMapping("/{id}")
	public ResponseMessage<?,?> deleteNode(@PathVariable("id") Integer id){
		menuService.removeById(id);
		return ResponseMessage.success("删除成功！");
	}
}

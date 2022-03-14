package com.crux.crowd.admin.component.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crux.crowd.admin.component.service.RoleService;
import com.crux.crowd.admin.entity.Role;
import com.crux.crowd.common.util.ResponseMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * 处理角色请求
 */
@RestController
@RequestMapping("/admin/main/role")
public class RoleController{

	private final RoleService roleService;

	public RoleController(RoleService roleService){
		this.roleService = roleService;
	}

	/**
	 * 获取role分页信息
	 * @param current 当前页
	 * @param size 分页大小
	 * @param keyword 模糊查询的关键词
	 * @return role的分页信息
	 */
	@GetMapping(params = "current")
	public ResponseMessage<String,Page<Role>> getRolePage(@RequestParam("current") int current,
											@RequestParam(name = "size", required = false, defaultValue = "10") int size,
											@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword){
		Page<Role> rolePage = roleService.pageFuzzy(current, size, keyword);
		String message = "查询到" + rolePage.getTotal() + "条数据";
		return ResponseMessage.success(message, Collections.singletonMap("page", rolePage));
	}
}
package com.crux.crowd.admin.component.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crux.crowd.admin.component.service.RoleService;
import com.crux.crowd.admin.entity.Role;
import com.crux.crowd.common.util.ResponseMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static java.util.Collections.singletonMap;
import static java.util.Collections.emptyMap;
import java.util.List;

/**
 * 角色维护处理器
 */
@RestController
@RequestMapping("/admin/main/role")
public class RoleController{

	private final RoleService roleService;
	static final String hasAnyRole = "hasAnyRole('部长', '超级管理员')";

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
	@PreAuthorize(hasAnyRole + " or hasAuthority('role:get')")
	public ResponseMessage<String,Page<Role>> getRolePage(@RequestParam("current") int current,
											@RequestParam(name = "size", required = false, defaultValue = "10") int size,
											@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword){
		Page<Role> rolePage = roleService.pageFuzzy(current, size, keyword);
		String message = "查询到" + rolePage.getTotal() + "条数据";
		return ResponseMessage.success(message, singletonMap("page", rolePage));
	}

	/**
	 * 通过id获取角色信息
	 * @param id 角色id
	 * @return 角色信息
	 */
	@GetMapping("/{id}")
	@PreAuthorize(hasAnyRole + " or hasAuthority('role:get')")
	public ResponseMessage<String,Role> getRole(@PathVariable("id") Integer id){
		Role role = roleService.getById(id);
		return ResponseMessage.success(singletonMap("role", role));
	}

	/**
	 * 通过id批量查询角色信息
	 * @param ids id集合
	 * @return 查询到的所有角色信息集合
	 */
	@GetMapping(params = "ids[]")
	@PreAuthorize(hasAnyRole + " or hasAuthority('role:get')")
	public ResponseMessage<String,List<Role>> getRoleList(@RequestParam("ids[]") List<Integer> ids){
		List<Role> roles = roleService.listByIds(ids);
		return roles.isEmpty() ? ResponseMessage.failure("没有查询到数据", emptyMap())
							   : ResponseMessage.success(singletonMap("roles", roles));
	}

	/**
	 * 如果role的id已存在，执行更新操作；否则执行添加操作
	 * @param role 角色信息
	 * @return 如果保存成功
	 */
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	@PreAuthorize(hasAnyRole + " or hasAuthority('role:save')")
	public ResponseMessage<?,?> saveOrUpdateRole(Role role){
		roleService.saveOrUpdate(role);
		return ResponseMessage.success("保存成功");
	}

	/**
	 * 根据id集合批量删除
	 * @param ids 要删除的id的集合
	 * @return 如果删除成功
	 */
	@DeleteMapping
	@PreAuthorize(hasAnyRole + " or hasAuthority('role:delete')")
	public ResponseMessage<?,?> deleteRole(@RequestParam("ids[]") List<Integer> ids){
		roleService.removeBatchByIds(ids);
		return ResponseMessage.success("删除成功");
	}
}
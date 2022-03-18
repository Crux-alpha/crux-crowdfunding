package com.crux.crowd.admin.component.controller;

import com.crux.crowd.admin.component.service.AdminService;
import com.crux.crowd.admin.component.service.RoleService;
import com.crux.crowd.admin.entity.Role;
import com.crux.crowd.common.util.ResponseMessage;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限分配处理器
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/admin/main/assign")
public class AssignController{

	private final AdminService adminService;
	private final RoleService roleService;

	public AssignController(AdminService adminService, RoleService roleService){
		this.adminService = adminService;
		this.roleService = roleService;
	}

	/**
	 * 获取指定admin的角色分配信息
	 * @param adminId Admin id
	 * @return 包含已分配角色列表和未分配角色列表信息
	 */
	@GetMapping("/admin/{adminId}")
	public ResponseMessage<String,List<Role>> getRolesAssigned(@PathVariable("adminId") Integer adminId){
		Map<String,List<Role>> roleAssignedInfo = new HashMap<>();

		List<Role> assignedRoles = roleService.getRolesAssigned(adminId, true);
		List<Role> unassignedRoles = roleService.getRolesAssigned(adminId, false);
		roleAssignedInfo.put("assigned", assignedRoles);
		roleAssignedInfo.put("unassigned", unassignedRoles);
		return ResponseMessage.success(roleAssignedInfo);
	}

	/**
	 * 为admin分配角色
	 * @param adminId Admin id
	 * @param roleIds 要分配的角色id集合
	 */
	@PostMapping("/admin/{adminId}")
	public ResponseMessage<?,?> saveAssignRoles(@PathVariable("adminId") Integer adminId,
												@RequestParam(name = "roleIds[]", required = false) List<Integer> roleIds){
		adminService.updateRolesAssigned(adminId, roleIds);
		return ResponseMessage.success("保存成功！");
	}
}

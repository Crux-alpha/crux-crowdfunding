package com.crux.crowd.admin.component.controller;

import com.crux.crowd.admin.component.service.AdminService;
import com.crux.crowd.admin.component.service.AuthService;
import com.crux.crowd.admin.component.service.RoleService;
import com.crux.crowd.admin.entity.Auth;
import com.crux.crowd.admin.entity.Role;
import com.crux.crowd.common.util.ResponseMessage;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.singletonMap;

/**
 * 权限分配处理器
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/admin/main/assign")
public class AssignController{

	private final AdminService adminService;
	private final RoleService roleService;
	private final AuthService authService;

	public AssignController(AdminService adminService, RoleService roleService, AuthService authService){
		this.adminService = adminService;
		this.roleService = roleService;
		this.authService = authService;
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

	/**
	 * 获取全部权限信息
	 * @return 所有的权限信息
	 */
	@GetMapping("/role")
	public ResponseMessage<String,List<Auth>> getAuths(){
		List<Auth> auths = authService.list();
		return ResponseMessage.success(singletonMap("nodes", auths));
	}

	@GetMapping("/role/{roleId}/auth_ids")
	public ResponseMessage<String,Set<Integer>> getAuthIdsAssigned(@PathVariable("roleId") Integer roleId){
		Set<Integer> authIds = authService.getAuthIds(roleId);
		return ResponseMessage.success(singletonMap("authIds", authIds));
	}

	@PostMapping("/role/{roleId}/auth_ids")
	public ResponseMessage<?,?> saveAssignAuths(@PathVariable("roleId") Integer roleId,
													@RequestParam(value = "authIds[]", required = false) List<Integer> authIds){
		authService.updateAuthsAssigned(roleId, authIds);
		return ResponseMessage.success("保存成功！");
	}
}

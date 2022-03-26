package com.crux.crowd.admin.component.controller;

import com.crux.crowd.admin.component.service.AdminService;
import com.crux.crowd.admin.component.service.AuthService;
import com.crux.crowd.admin.component.service.RoleService;
import com.crux.crowd.admin.entity.Auth;
import com.crux.crowd.admin.entity.Role;
import com.crux.crowd.common.util.ResponseMessage;
import org.springframework.security.access.prepost.PreAuthorize;
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
	@PreAuthorize(AdminController.hasAnyRole + " or hasAuthority('user:get')")
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
	@PreAuthorize(AdminController.hasAnyRole + " or (hasAuthority('user:save') and hasAuthority('role:get'))")
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
	@PreAuthorize(RoleController.hasAnyRole + " or hasAuthority('role:get')")
	public ResponseMessage<String,List<Auth>> getAuths(){
		List<Auth> auths = authService.list();
		return ResponseMessage.success(singletonMap("nodes", auths));
	}

	/**
	 * 根据角色id获取该角色具有的权限
	 * @param roleId 角色id
	 * @return 该角色具有的权限id
	 */
	@GetMapping("/role/{roleId}/auth_ids")
	@PreAuthorize(RoleController.hasAnyRole + " or hasAuthority('role:get')")
	public ResponseMessage<String,Set<Integer>> getAuthIdsAssigned(@PathVariable("roleId") Integer roleId){
		Set<Integer> authIds = authService.getAuthIds(roleId);
		return ResponseMessage.success(singletonMap("authIds", authIds));
	}

	/**
	 * 为指定角色分配权限
	 * @param roleId 角色id
	 * @param authIds 权限id集合
	 */
	@PostMapping("/role/{roleId}/auth_ids")
	@PreAuthorize(RoleController.hasAnyRole + " or hasAuthority('role:save')")
	public ResponseMessage<?,?> saveAssignAuths(@PathVariable("roleId") Integer roleId,
													@RequestParam(value = "authIds[]", required = false) List<Integer> authIds){
		authService.updateAuthsAssigned(roleId, authIds);
		return ResponseMessage.success("保存成功！");
	}
}

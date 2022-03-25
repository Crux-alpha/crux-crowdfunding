package com.crux.crowd.admin.component.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.crux.crowd.admin.component.service.AdminNotFoundException;
import com.crux.crowd.admin.component.service.AdminService;
import com.crux.crowd.admin.component.service.AuthService;
import com.crux.crowd.admin.component.service.RoleService;
import com.crux.crowd.admin.component.service.details.AdminDetails;
import com.crux.crowd.admin.entity.Admin;
import com.crux.crowd.admin.entity.Auth;
import com.crux.crowd.admin.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

	private AdminService adminService;
	private RoleService roleService;
	private AuthService authService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		final Admin admin;

		try{
			admin = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getLoginAcct, username));
		}catch(AdminNotFoundException e){
			throw new UsernameNotFoundException(e.getMessage(), e);
		}

		return load(admin);
	}

	private AdminDetails load(Admin admin){
		// 1、拿到admin所有的角色
		List<Role> assignRoles = roleService.getRolesAssigned(admin.getId(), true);
		// 2、从所有角色中再获取角色具有的权限。使用Set自动去重
		List<Integer> roleIds = assignRoles.stream().map(Role::getId).collect(Collectors.toList());
		Set<Integer> authIds = authService.listAuthIds(roleIds);
		// 3、拿到所有权限
		List<Auth> auths = authService.listByIds(authIds);

		final List<GrantedAuthority> authorities = new ArrayList<>();
		// 4、拿到角色名，拼接前缀"ROLE_"，装进SimpleGrantedAuthority中并加入到authorities
		assignRoles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).forEach(authorities::add);
		// 5、拿到权限名,加入到authorities
		auths.stream().map(auth -> new SimpleGrantedAuthority(auth.getName())).forEach(authorities::add);

		return new AdminDetails(admin, authorities);
	}

	@Autowired
	public void setAdminService(AdminService adminService){
		this.adminService = adminService;
	}

	@Autowired
	public void setRoleService(RoleService roleService){
		this.roleService = roleService;
	}

	@Autowired
	public void setAuthService(AuthService authService){
		this.authService = authService;
	}
}

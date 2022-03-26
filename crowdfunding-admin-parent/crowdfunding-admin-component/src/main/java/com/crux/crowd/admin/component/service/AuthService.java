package com.crux.crowd.admin.component.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.crowd.admin.entity.Auth;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 权限业务层
 * @since 2022-03-19
 */
public interface AuthService extends IService<Auth>{

	/**
	 * 根据角色id查询权限的id
	 * 通过inner_role_auth查询
	 * @param roleId 角色id
	 * @return 该角色具有的权限
	 */
	Set<Integer> getAuthIds(Integer roleId);

	/**
	 * 批量查找权限id
	 * @param roleIds 角色id集合
	 * @return 这些角色具有的权限
	 */
	Set<Integer> listAuthIds(Collection<Integer> roleIds);

	/**
	 * 修改指定角色的权限
	 * @param roleId 角色id
	 * @param authIds 权限id集合
	 * @return 执行结果
	 */
	boolean updateAuthsAssigned(Integer roleId, List<Integer> authIds);
}

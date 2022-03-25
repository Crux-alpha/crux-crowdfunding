package com.crux.crowd.admin.component.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crux.crowd.admin.entity.Auth;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 权限表的mapper
 * @since 2022-03-19
 */
public interface AuthMapper extends BaseMapper<Auth>{

	Set<Integer> selectAuthIdsByRoleId(Collection<Integer> roleIds);

	int deleteAuthByRoleId(Integer roleId);

	int insertAuthByRoleId(Integer roleId, List<Integer> authIds);
}

package com.crux.crowd.admin.component.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crux.crowd.admin.entity.Auth;

import java.util.List;

/**
 * 权限表的mapper
 * @since 2022-03-19
 */
public interface AuthMapper extends BaseMapper<Auth>{

	List<Integer> selectAuthIdsByRoleId(Integer roleId);

	int deleteAuthByRoleId(Integer roleId);

	int insertAuthByRoleId(Integer roleId, List<Integer> authIds);
}

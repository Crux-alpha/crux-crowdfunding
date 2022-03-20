package com.crux.crowd.admin.component.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.crux.crowd.admin.component.mapper.AuthMapper;
import com.crux.crowd.admin.component.service.AbstractService;
import com.crux.crowd.admin.component.service.AuthService;
import com.crux.crowd.admin.component.service.ServiceException;
import com.crux.crowd.admin.entity.Auth;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

@Service("authService")
public class AuthServiceImpl extends AbstractService<AuthMapper,Auth> implements AuthService{

	@Override
	public List<Integer> getAuthIds(Integer roleId){
		return baseMapper.selectAuthIdsByRoleId(roleId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public boolean updateAuthsAssigned(final Integer roleId, List<Integer> authIds){
		synchronized(roleId){
			// 1、删除角色所有权限
			baseMapper.deleteAuthByRoleId(roleId);

			// 2、如果没有新的权限，则完成操作
			if(authIds == null || authIds.isEmpty()) return true;

			// 3、重新赋予权限
			return SqlHelper.retBool(baseMapper.insertAuthByRoleId(roleId, authIds));
		}
	}

	@Override
	protected <R> R execute(Supplier<R> method) throws ServiceException{
		return method.get();
	}
}

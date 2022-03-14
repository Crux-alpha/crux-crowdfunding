package com.crux.crowd.admin.component.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crux.crowd.admin.component.mapper.RoleMapper;
import com.crux.crowd.admin.component.service.AbstractService;
import com.crux.crowd.admin.component.service.RoleService;
import com.crux.crowd.admin.component.service.ServiceException;
import com.crux.crowd.admin.entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.function.Supplier;

@Service("roleService")
public class RoleServiceImpl extends AbstractService<RoleMapper,Role> implements RoleService{

	@Override
	public Page<Role> pageFuzzy(int current, int size, String keyword){
		LambdaQueryWrapper<Role> wrapper = lambdaQueryWrapper();
		if(StringUtils.hasLength(keyword)){
			wrapper = wrapper.like(Role::getName, keyword);
		}
		return page(new Page<>(current, size), wrapper);
	}

	@Override
	protected <R> R execute(Supplier<R> method) throws ServiceException{
		return method.get();
	}
}

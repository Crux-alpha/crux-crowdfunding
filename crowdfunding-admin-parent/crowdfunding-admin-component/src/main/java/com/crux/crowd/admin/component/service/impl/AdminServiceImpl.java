package com.crux.crowd.admin.component.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.crux.crowd.admin.component.service.*;
import com.crux.crowd.admin.entity.Admin;
import com.crux.crowd.admin.component.mapper.AdminMapper;
import com.crux.crowd.common.util.CrowdConstant;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * admin业务层
 * @since 2022-03-09
 */
@Service("adminService")
public class AdminServiceImpl extends AbstractService<AdminMapper,Admin> implements AdminService{

	@Override
	public Admin login(String account, String password){
		// 1、根据用户名获取Admin
		Admin admin = getOne(lambdaQueryWrapper().eq(Admin::getLoginAcct, account));
		// 2、检查Admin是否为空
		Optional.ofNullable(admin).orElseThrow(() -> new LoginFailedException(CrowdConstant.TipsMessage.ACCOUNT_NOT_FOUNT));
		// 3、加密password
		String encoded = DigestUtils.md5DigestAsHex(password.getBytes());
		// 4、与Admin的密码进行比较
		Optional.ofNullable(admin.getUserPswd()).filter(encoded::equalsIgnoreCase)
				.orElseThrow(LoginFailedException::new);
		// 5、校验成功，返回Admin
		return admin;
	}

	@Override
	public Page<Admin> pageFuzzy(int current, int size, String keyword){
		LambdaQueryWrapper<Admin> wrapper = lambdaQueryWrapper();
		if(StringUtils.hasLength(keyword)){
			wrapper = wrapper.like(Admin::getLoginAcct, keyword).or().like(Admin::getUserName, keyword).or().like(Admin::getEmail, keyword);
		}
		return page(new Page<>(current, size), wrapper);
	}

	@Override
	public boolean update(int id, String username, String email){
		return update(lambdaUpdateWrapper().set(Admin::getUserName, username).set(Admin::getEmail, email).eq(Admin::getId, id));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public boolean updateRolesAssigned(final Integer id, List<Integer> roleIds){
		synchronized(id){
			// 1、先删除该admin所有分配的角色
			baseMapper.deleteAssignRolesById(id);

			// 2、如果没有分配角色，操作完成
			if(roleIds == null || roleIds.isEmpty()) return true;

			// 3、执行插入操作
			return SqlHelper.retBool(baseMapper.insertAssignRolesById(id, roleIds));
		}
	}

	@Override
	protected <R> R execute(Supplier<R> method) throws LoginAccountRepeatedException{
		try{
			return method.get();
		}catch(DuplicateKeyException e){
			String message = Optional.ofNullable(e.getMessage()).orElse("");
			String loginAcct = "login_acct";
			if(message.contains(loginAcct)) throw new LoginAccountRepeatedException(CrowdConstant.TipsMessage.ACCOUNT_IN_USE);

			throw e;
		}
	}
}
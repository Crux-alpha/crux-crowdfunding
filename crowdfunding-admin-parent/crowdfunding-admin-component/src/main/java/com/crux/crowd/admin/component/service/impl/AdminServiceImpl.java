package com.crux.crowd.admin.component.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crux.crowd.admin.component.service.LoginAccountRepeatedException;
import com.crux.crowd.admin.component.service.LoginFailedException;
import com.crux.crowd.admin.entity.Admin;
import com.crux.crowd.admin.component.mapper.AdminMapper;
import com.crux.crowd.admin.component.service.AdminService;
import com.crux.crowd.common.util.CrowdConstant;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-03-09
 */
@Service("adminService")
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements AdminService{

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
	public Page<Admin> pageFuzzy(String keyword, int current, int size){
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
	public boolean save(final Admin entity){
		return execute(() -> super.save(entity));
	}

	@Override
	public boolean updateById(final Admin entity){
		return execute(() -> super.updateById(entity));
	}

	@Override
	public boolean update(final Wrapper<Admin> updateWrapper){
		return execute(() -> super.update(updateWrapper));
	}

	@Override
	public boolean update(final Admin entity, final Wrapper<Admin> updateWrapper){
		return execute(() -> super.update(entity, updateWrapper));
	}

	/**
	 * 统一包装save/update方法抛出的异常
	 * @param method save/update方法
	 * @param <T> 返回值类型
	 * @return method的返回值
	 * @throws LoginAccountRepeatedException 如果账号已存在
	 */
	private static <T> T execute(Supplier<T> method) throws LoginAccountRepeatedException{
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
package com.crux.crowd.admin.component.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.crowd.admin.entity.Admin;

import java.io.Serializable;
import java.util.Optional;

/**
 * Admin业务层组件
 * @author crux
 * @since 2022/03/09
 */
public interface AdminService extends IService<Admin>{

	/**
	 * 根据用户名和密码进行登录校验。
	 * 如果用户名不存在，或者密码不匹配，则登录失败
	 * @param account 用户名
	 * @param password 密码
	 * @return 登录成功的Admin对象
	 * @throws LoginFailedException 如果验证失败，则阻止登录，并提示失败信息 --> {@link LoginFailedException#getMessage()}。
	 */
	Admin login(String account, String password) throws LoginFailedException;

	Page<Admin> pageFuzzy(String keyword, int current, int size);

	boolean update(int id, String username, String email);

	default LambdaQueryWrapper<Admin> lambdaQueryWrapper(){
		return (LambdaQueryWrapper<Admin>)lambdaQuery().getWrapper();
	}

	default QueryWrapper<Admin> queryWrapper(){
		return (QueryWrapper<Admin>)query().getWrapper();
	}

	default LambdaUpdateWrapper<Admin> lambdaUpdateWrapper(){
		return (LambdaUpdateWrapper<Admin>)lambdaUpdate().getWrapper();
	}

	default UpdateWrapper<Admin> updateWrapper(){
		return (UpdateWrapper<Admin>)update().getWrapper();
	}

	default Admin getById(final Serializable id){
		return Optional.ofNullable(IService.super.getById(id)).orElseThrow(() -> new AdminNotFoundException("没有找到id为" + id + "的用户"));
	}

	default Admin getOne(Wrapper<Admin> queryWrapper){
		return Optional.ofNullable(IService.super.getOne(queryWrapper)).orElseThrow(() -> new AdminNotFoundException("没有找到符合条件的用户"));
	}
}

package com.crux.crowd.admin.component.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
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

	/**
	 * 根据页码、分页大小、关键词查询并分页
	 * @param current 当前页码
	 * @param size 分页大小
	 * @param keyword 关键词
	 * @return 查询到的分页信息
	 */
	Page<Admin> pageFuzzy(int current, int size, String keyword);

	/**
	 * 根据id修改指定admin的username和email
	 * @param id id
	 * @param username 修改昵称
	 * @param email 修改邮箱
	 * @return 如果修改成功，返回true
	 */
	boolean update(int id, String username, String email);

	default Admin getById(final Serializable id){
		return Optional.ofNullable(IService.super.getById(id)).orElseThrow(() -> new AdminNotFoundException("没有找到id为" + id + "的用户"));
	}

	default Admin getOne(Wrapper<Admin> queryWrapper){
		return Optional.ofNullable(IService.super.getOne(queryWrapper)).orElseThrow(() -> new AdminNotFoundException("没有找到符合条件的用户"));
	}
}

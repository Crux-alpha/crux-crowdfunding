package com.crux.crowd.admin.component.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.crowd.admin.entity.Admin;
import com.crux.crowd.common.LoginFailedException;

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

}

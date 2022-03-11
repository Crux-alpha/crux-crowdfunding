package com.crux.crowd.admin.component.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crux.crowd.admin.entity.Admin;
import com.crux.crowd.admin.component.mapper.AdminMapper;
import com.crux.crowd.admin.component.service.AdminService;
import com.crux.crowd.common.LoginFailedException;
import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.common.util.CrowdUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
		Admin admin = getOne(lambdaQuery().getWrapper().eq(Admin::getLoginAcct, account));
		// 2、检查Admin是否为空
		if(admin == null) throw new LoginFailedException(CrowdConstant.TipsMessage.ACCOUNT_NOT_FOUNT);
		// 3、加密password
		String encoded = CrowdUtils.md5Encryption(password);
		// 4、与Admin的密码进行比较
		if(!Objects.equals(admin.getUserPswd(), encoded)) throw new LoginFailedException(CrowdConstant.TipsMessage.LOGIN_FAILED);
		// 5、校验成功，返回Admin
		return admin;
	}
}

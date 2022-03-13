package com.crux.crowd.admin.component.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crux.crowd.admin.component.service.LoginFailedException;
import com.crux.crowd.admin.entity.Admin;
import com.crux.crowd.admin.component.mapper.AdminMapper;
import com.crux.crowd.admin.component.service.AdminService;
import com.crux.crowd.common.util.CrowdConstant;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Optional;

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
		Admin admin = getOne(getLambdaQueryWrapper().eq(Admin::getLoginAcct, account));
		// 2、检查Admin是否为空
		if(admin == null) throw new LoginFailedException(CrowdConstant.TipsMessage.ACCOUNT_NOT_FOUNT);
		// 3、加密password
		String encoded = DigestUtils.md5DigestAsHex(password.getBytes());
		// 4、与Admin的密码进行比较
		Optional.ofNullable(admin.getUserPswd()).filter(encoded::equalsIgnoreCase)
				.orElseThrow(() -> new LoginFailedException(CrowdConstant.TipsMessage.LOGIN_FAILED));
		// 5、校验成功，返回Admin
		return admin;
	}

	@Override
	public Page<Admin> pageFuzzy(String keyword, int current, int size){
		LambdaQueryWrapper<Admin> wrapper = getLambdaQueryWrapper();
		if(StringUtils.hasLength(keyword)){
			wrapper = wrapper.like(Admin::getLoginAcct, keyword).or().like(Admin::getUserName, keyword).or().like(Admin::getEmail, keyword);
		}
		return page(new Page<>(current, size), wrapper);
	}


	/* -----------------以下方法为AdminServiceProxy的切入点--------------- */
	@Override
	public boolean save(Admin entity){
		return super.save(entity);
	}

	@Override
	public boolean saveBatch(Collection<Admin> entityList){
		return super.saveBatch(entityList);
	}

	@Override
	public boolean saveBatch(Collection<Admin> entityList, int batchSize){
		return super.saveBatch(entityList, batchSize);
	}

	@Override
	public boolean saveOrUpdate(Admin entity){
		return super.saveOrUpdate(entity);
	}

	@Override
	public boolean saveOrUpdateBatch(Collection<Admin> entityList, int batchSize){
		return super.saveOrUpdateBatch(entityList, batchSize);
	}

	@Override
	public boolean saveOrUpdate(Admin entity, Wrapper<Admin> updateWrapper){
		return super.saveOrUpdate(entity, updateWrapper);
	}

	@Override
	public boolean saveOrUpdateBatch(Collection<Admin> entityList){
		return super.saveOrUpdateBatch(entityList);
	}

	@Override
	public boolean update(Wrapper<Admin> updateWrapper){
		return super.update(updateWrapper);
	}

	@Override
	public boolean update(Admin entity, Wrapper<Admin> updateWrapper){
		return super.update(entity, updateWrapper);
	}

	@Override
	public UpdateChainWrapper<Admin> update(){
		return super.update();
	}

	@Override
	public boolean updateBatchById(Collection<Admin> entityList){
		return super.updateBatchById(entityList);
	}

	@Override
	public boolean updateById(Admin entity){
		return super.updateById(entity);
	}

	@Override
	public boolean updateBatchById(Collection<Admin> entityList, int batchSize){
		return super.updateBatchById(entityList, batchSize);
	}
}
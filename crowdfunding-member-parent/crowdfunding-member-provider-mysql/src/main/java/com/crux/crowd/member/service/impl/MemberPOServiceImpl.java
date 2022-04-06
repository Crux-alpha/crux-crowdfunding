package com.crux.crowd.member.service.impl;

import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.member.entity.po.MemberPO;
import com.crux.crowd.member.mapper.MemberPOMapper;
import com.crux.crowd.member.service.*;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service("memberPOService")
public class MemberPOServiceImpl extends AbstractService<MemberPOMapper,MemberPO> implements MemberPOService{

	@Override
	public MemberPO getByLoginAcctOrPhone(String accountOrPhone){
		return getOne(lambdaQueryWrapper().eq(MemberPO::getLoginAcct, accountOrPhone).or().eq(MemberPO::getPhone, accountOrPhone));
	}

	@Override
	protected <R> R execute(Supplier<R> method) throws ServiceException{
		try{
			return method.get();
		}catch(DuplicateKeyException e){
			String message = Optional.ofNullable(e.getMessage()).orElse("");

			String phoneUniqueKey = "'phone_unique'";
			String loginAcctUniqueKey = "'login_acct_unique'";

			if(message.contains(loginAcctUniqueKey)) throw new LoginAccountRepeatedException(CrowdConstant.TipsMessage.ACCOUNT_IN_USE);
			else if(message.contains(phoneUniqueKey)) throw new PhoneRepeatedException(CrowdConstant.TipsMessage.PHONE_IN_USE);

			throw new ServiceException("业务异常，嵌套原因是：" + e.getClass().getName(), e);
		}
	}
}

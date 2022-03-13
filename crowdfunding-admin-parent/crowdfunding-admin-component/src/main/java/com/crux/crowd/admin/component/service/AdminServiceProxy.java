package com.crux.crowd.admin.component.service;

import com.crux.crowd.common.util.CrowdConstant;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

/**
 * 代理AdminService的save/update方法。当这些方法抛出异常，将其包装成业务异常
 * @since 2022/03/13
 */
@Component
@Aspect
public class AdminServiceProxy{

	@AfterThrowing(pointcut = "execution(* com.crux.crowd.admin..AdminServiceImpl.save*(..))", throwing = "e")
	public void saveAfterThrowing(Exception e) throws LoginAccountRepeatedException{
		threwOtherException(e);
	}

	@AfterThrowing(pointcut = "execution(* com.crux.crowd.admin..AdminServiceImpl.update*(..))", throwing = "e")
	public void updateAfterThrowing(Exception e) throws LoginAccountRepeatedException{
		threwOtherException(e);
	}

	private void threwOtherException(Exception e){
		if(e instanceof DuplicateKeyException){
			String message = e.getMessage();
			if(message.contains("login_acct")) throw new LoginAccountRepeatedException(CrowdConstant.TipsMessage.ACCOUNT_IN_USE, e);
		}
	}

}

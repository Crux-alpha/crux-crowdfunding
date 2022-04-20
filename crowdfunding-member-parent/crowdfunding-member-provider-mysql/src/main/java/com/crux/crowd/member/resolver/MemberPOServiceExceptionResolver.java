package com.crux.crowd.member.resolver;

import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.controller.MemberPOProviderController;
import com.crux.crowd.member.service.LoginAccountRepeatedException;
import com.crux.crowd.member.service.PhoneRepeatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = MemberPOProviderController.class)
public class MemberPOServiceExceptionResolver{

	/**
	 * 账号重复处理器
	 */
	@ExceptionHandler(LoginAccountRepeatedException.class)
	public ResultEntity<?,?> saveMemberFailedHandler(LoginAccountRepeatedException e){
		// response.setStatus(500);
		log.warn(e.getMessage());
		return ResultEntity.failure(e.getMessage());
	}

	@ExceptionHandler(PhoneRepeatedException.class)
	public ResultEntity<?,?> saveMemberFailedHandler(PhoneRepeatedException e){
		log.warn(e.getMessage());
		return ResultEntity.failure(e.getMessage());
	}
}

package com.crux.crowd.member.resolver;

import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.controller.MemberPOProviderController;
import com.crux.crowd.member.service.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = MemberPOProviderController.class)
public class ServiceExceptionResolver{

	@ExceptionHandler(ServiceException.class)
	public ResultEntity<String,String> serviceExceptionHandler(ServiceException e){
		log.error("业务异常，嵌套原因是：{}", e.getMessage());
		e.printStackTrace();
		return ResultEntity.error(e.getMessage());
	}
}

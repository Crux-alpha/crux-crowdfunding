package com.crux.crowd.member.resolver;

import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.controller.OrderProviderController;
import com.crux.crowd.member.controller.ProjectProviderController;
import com.crux.crowd.member.service.ServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {ProjectProviderController.class, OrderProviderController.class})
public class ServiceExceptionResolver{

	@ExceptionHandler(ServiceException.class)
	public ResultEntity<String,String> saveProjectExceptionHandler(ServiceException e){
		e.printStackTrace();
		return ResultEntity.error(e.getMessage());
	}
}

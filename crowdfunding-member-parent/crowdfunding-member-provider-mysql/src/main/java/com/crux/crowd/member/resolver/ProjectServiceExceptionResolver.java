package com.crux.crowd.member.resolver;

import static com.crux.crowd.member.resolver.MemberPOServiceExceptionResolver.responseToJson;
import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.controller.ProjectProviderController;
import com.crux.crowd.member.service.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice(assignableTypes = ProjectProviderController.class)
public class ProjectServiceExceptionResolver{

	@ExceptionHandler(ServiceException.class)
	public void saveProjectExceptionHandler(ServiceException e, HttpServletResponse response) throws IOException{
		responseToJson(ResultEntity.failure(e.getMessage()), response);
	}

}

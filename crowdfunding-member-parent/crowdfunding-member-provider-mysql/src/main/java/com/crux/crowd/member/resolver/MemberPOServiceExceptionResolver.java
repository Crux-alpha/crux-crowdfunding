package com.crux.crowd.member.resolver;

import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.controller.MemberPOProviderController;
import com.crux.crowd.member.controller.ProjectProviderController;
import com.crux.crowd.member.service.LoginAccountRepeatedException;
import com.crux.crowd.member.service.PhoneRepeatedException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@ControllerAdvice(assignableTypes = MemberPOProviderController.class)
public class MemberPOServiceExceptionResolver{

	static final Gson gson = new Gson();

	/**
	 * 账号重复处理器
	 */
	@ExceptionHandler(LoginAccountRepeatedException.class)
	public void saveMemberFailedHandler(LoginAccountRepeatedException e, HttpServletResponse response) throws IOException{
		// response.setStatus(500);
		log.warn(e.getMessage());
		responseToJson(ResultEntity.failure(e.getMessage()), response);
	}

	@ExceptionHandler(PhoneRepeatedException.class)
	public void saveMemberFailedHandler(PhoneRepeatedException e, HttpServletResponse response) throws IOException{
		log.warn(e.getMessage());
		responseToJson(ResultEntity.failure(e.getMessage()), response);
	}

	static void responseToJson(ResultEntity<?,?> responseMessage, HttpServletResponse response) throws IOException{
		String json = gson.toJson(responseMessage);
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.getWriter().write(json);
	}

}

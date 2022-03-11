package com.crux.crowd.admin.component.resolver;

import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.common.util.CrowdUtils;
import com.crux.crowd.common.util.ResponseMessage;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Admin异常处理器
 */
@ControllerAdvice
public class AdminExceptionResolver{
	static final Gson gson = new Gson();

	@ExceptionHandler(NullPointerException.class)
	public ModelAndView nullPointerExceptionHandler(NullPointerException e, HttpServletRequest request, HttpServletResponse response) throws IOException{
		return distribute(ResponseMessage.failure(e.getMessage()), e, request, response);
	}

	/**
	 * JSON请求和普通请求分发处理
	 * @param responseMessage 响应数据
	 * @param e 异常
	 * @return 如果是JSON请求，不返回ModelAndView，而是直接根据<code>responseMessage</code>输出JSON数据
	 * @throws IOException 如果不能正常输出JSON数据
	 */
	private static ModelAndView distribute(ResponseMessage<?> responseMessage, Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(CrowdUtils.isJSONRequest(request)){
			responseToJson(responseMessage, response);
			return null;
		}
		return responseToView(e);
	}

	private static void responseToJson(ResponseMessage<?> responseMessage, HttpServletResponse response) throws IOException{
		String json = gson.toJson(responseMessage);
		response.getWriter().write(json);
	}

	private static ModelAndView responseToView(Exception e){
		return responseToView(e, "/error/5xx", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private static ModelAndView responseToView(Exception e, String viewName, HttpStatus status){
		Map<String,Object> model = new HashMap<>();
		model.put(CrowdConstant.STATUS, 500);
		model.put(CrowdConstant.MESSAGE, e.getMessage());
		model.put(CrowdConstant.EXCEPTION, e.getClass().getName());
		//Map<String,Object> model = Stream.of("status=500", "message=" + e.getMessage(), "exception=" + e.getClass().getName())
				//.map(s -> s.split("=")).collect(Collectors.toMap(s -> s[0], s -> s[1]));
		return new ModelAndView(viewName, model, status);
	}
}

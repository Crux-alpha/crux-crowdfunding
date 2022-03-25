package com.crux.crowd.admin.component.interceptor;

import com.crux.crowd.admin.component.service.AccessDeniedException;
import com.crux.crowd.common.util.CrowdConstant;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 对后台资源进行登录检查
 * 已弃用，由SpringSecurity管理
 * @since 2022/03/11
 */
@Deprecated
public class LoginInterceptor implements HandlerInterceptor{

	/**
	 * 检查/admin/**路径。如果未登录，将拒绝访问
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		// 1、获取session对象
		HttpSession session = request.getSession();
		// 2、获取admin
		Object admin = session.getAttribute(CrowdConstant.ADMIN_LOGIN_ACCOUNT);
		// 3、判断admin是否存在。如果不存在，拒绝访问
		if(admin == null) throw new AccessDeniedException(CrowdConstant.TipsMessage.ACCESS_FORBIDDEN);
		// 4、admin存在，放行
		return true;
	}
}

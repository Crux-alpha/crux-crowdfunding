package com.crux.crowd.member.filter;

import static com.crux.crowd.common.util.CrowdConstant.*;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@EnableConfigurationProperties(LoginCheckedZuulFilter.LoginReleasePathProperties.class)
public class LoginCheckedZuulFilter extends ZuulFilter{

	private static final Logger log = LoggerFactory.getLogger(LoginCheckedZuulFilter.class);
	/**
	 * 可放行的路径集合
	 */
	static final Set<String> RELEASE_PATH = Stream.of("/static/**", "/", "/error", "/favicon.ico").collect(Collectors.toSet());
	static final Set<String> RELEASE_PATH_LOGIN = new HashSet<>(RELEASE_PATH);

	@Override
	public String filterType(){
		return "pre";
	}

	@Override
	public int filterOrder(){
		return 0;
	}

	/**
	 * 执行登录检查
	 * @return 如果为true，将拦截该请求，前往{@link #run()}
	 */
	@Override
	public boolean shouldFilter(){
		// 1、获取请求上下文对象
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();

		// 2、拿到当前访问的资源路径
		String requestURI = request.getRequestURI();
		// log.info(requestPath);

		// 3、如果为拦截的资源，并且请求域中没有保存memberInfo，拦截
		return isInterceptPath(requestURI) && Objects.isNull(request.getSession().getAttribute(SESSION_ATTRIBUTE_MEMBER_INFO));
	}

	/**
	 * 将请求重定向到登录页面
	 * @return Void
	 * @throws ZuulException 如果重定向抛出异常
	 */
	@Override
	public Object run() throws ZuulException{
		RequestContext context = RequestContext.getCurrentContext();
		// 1、拿到session，添加提示信息
		HttpSession session = context.getRequest().getSession();
		session.setAttribute(MESSAGE, TipsMessage.LOGIN_FAILED);
		// 2、重定向到登录页面
		HttpServletResponse response = context.getResponse();
		try{
			response.sendRedirect("/member/login.html");
		}catch(IOException e){
			log.error(e.getMessage());
			throw new ZuulException(e, "请求跳转时发生异常", 500, e.getMessage());
		}
		return null;
	}

	static boolean isInterceptPath(String requestURI){
		return !RELEASE_PATH_LOGIN.contains(requestURI) &&
				RELEASE_PATH_LOGIN.stream()
						.filter(path -> path.endsWith("/**")).map(path -> path.substring(0, path.indexOf("/**")))
						.noneMatch(requestURI::startsWith);
	}

	@ConfigurationProperties("crowd.login")
	static class LoginReleasePathProperties{

		public void setReleasePath(Set<String> releasePath){
			LoginCheckedZuulFilter.RELEASE_PATH_LOGIN.addAll(releasePath);
		}
	}
}

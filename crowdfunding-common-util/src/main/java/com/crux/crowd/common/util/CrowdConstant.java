package com.crux.crowd.common.util;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

/**
 * 众筹平台项目常量池
 * @since 2022/03/11
 */
public final class CrowdConstant{
	public static final String MESSAGE = "message";
	public static final String EXCEPTION = "exception";
	public static final String STATUS = "status";
	public static final String ADMIN_LOGIN_ACCOUNT = "loginAccount_admin";
	public static final String JSON_RESULT = "result_json";
	public static final DateTimeFormatter DEFAULT_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	public static final DateTimeFormatter NUMBER_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	public static final int DEFAULT_MESSAGE_TIMEOUT_MINUTES = 3;
	public static final String REDIS_MESSAGE_CODE_PREFIX = "message-code:";
	public static final String COOKIE_MESSAGE_CODE_INTERVAL = "message_code_interval";
	public static final String SESSION_ATTRIBUTE_MEMBER_INFO = "memberInfo";
	public static final String SESSION_ATTRIBUTE_PROJECT_DATA = "project_data";
	public static final String SESSION_ATTRIBUTE_ORDER_PROJECT = "orderProject";
	public static final String PORTAL_PROJECT_LIST = "portalProjectList";
	public static final String DETAIL_PROJECT = "detailProject";
	public static final String SESSION_ATTRIBUTE_ORDER = "order";
	public static final Duration REDIS_ORDER_DEFAULT_EXPIRE = Duration.ofHours(48);

	/**
	 * 与HTTP属性有关的常量池
	 */
	public static final class HttpAttribute{
		public static final String APP_JSON = "application/json";
		public static final String APP_XML_HTTP_REQUEST = "XMLHttpRequest";

		private HttpAttribute(){}
	}

	/**
	 * 提示信息常量池
	 */
	public static final class TipsMessage{

		public static final String LOGIN_FAILED = "用户名或密码错误";
		public static final String ACCOUNT_IN_USE = "账号已被使用";
		public static final String ACCOUNT_NOT_FOUNT = "账号不存在";
		public static final String ACCESS_FORBIDDEN = "您还没有登录，请重新登录！";
		public static final String STRING_EMPTY = "字符串不能为空";
		public static final String CODE_TIMEOUT = "验证码已过期，请重新发送！";
		public static final String CODE_INCONSISTENT = "验证码不一致，请重试！";
		public static final String SERVER_ERROR = "服务器出现异常，请稍后重试";
		public static final String PHONE_IN_USE = "该手机号已经被注册";
		public static final String HEADER_IMAGE_NOT_UPLOAD = "没有上传头图，请检查！";
		public static final String HEADER_IMAGE_UPLOAD_FAILED = "头图上传失败";
		public static final String HTML_FAILURE = "当前页面已失效，请刷新后重试";
		public static final String DATA_NOT_FOUNT = "查不到数据";

		private TipsMessage(){}
	}

	public static final class RoleName{
		public static final String ROLE_超级管理员 = "超级管理员";
		public static final String ROLE_经理 = "经理";
		public static final String ROLE_部长 = "部长";

		private RoleName(){}
	}

	private CrowdConstant(){}
}

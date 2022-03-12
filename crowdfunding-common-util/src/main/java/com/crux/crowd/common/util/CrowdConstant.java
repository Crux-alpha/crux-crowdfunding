package com.crux.crowd.common.util;

/**
 * 筹钱吧项目常量池
 * @since 2022/03/11
 */
public final class CrowdConstant{
	public static final String MESSAGE = "message";
	public static final String EXCEPTION = "exception";
	public static final String STATUS = "status";
	public static final String ADMIN_LOGIN_ACCOUNT = "loginAccount_admin";
	public static final String JSON_RESULT = "result_json";

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

		public static final String LOGIN_FAILED = "账号密码有误，请重新输入！";
		public static final String ACCOUNT_IN_USE = "账号已被使用";
		public static final String ACCOUNT_NOT_FOUNT = "账号不存在";
		public static final String ACCESS_FORBIDDEN = "您还没有登录，请重新登录！";
		public static final String STRING_EMPTY = "字符串不能为空";

		private TipsMessage(){}
	}


	private CrowdConstant(){}
}

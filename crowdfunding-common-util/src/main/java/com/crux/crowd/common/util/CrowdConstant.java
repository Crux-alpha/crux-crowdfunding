package com.crux.crowd.common.util;

public final class CrowdConstant{
	public static final String MESSAGE = "message";
	public static final String EXCEPTION = "exception";
	public static final String STATUS = "status";

	public static final class HttpAttribute{
		public static final String APP_JSON = "application/json";
		public static final String APP_XML_HTTP_REQUEST = "XMLHttpRequest";

		private HttpAttribute(){}
	}

	public static final class AdminMessage{

		public static final String LOGIN_FAILED = "账号密码有误，请重新输入！";
		public static final String ACCOUNT_IN_USE = "账号已被使用";
		public static final String ACCESS_FORBIDDEN = "您还没有登录，请重新登录！";

		private AdminMessage(){}
	}


	private CrowdConstant(){}
}

package com.crux.crowd.common.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public final class CrowdUtils{
	private CrowdUtils(){}

	public static boolean isJSONRequest(HttpServletRequest request){
		Optional<String> accept = Optional.ofNullable(request.getHeader("Accept"));
		Optional<String> xRequestedWith = Optional.ofNullable(request.getHeader("X-Requested-With"));
		return accept.map(CrowdConstant.HttpAttribute.APP_JSON::contains).orElse(false) ||
				xRequestedWith.map(CrowdConstant.HttpAttribute.APP_XML_HTTP_REQUEST::equals).orElse(false);
	}
}

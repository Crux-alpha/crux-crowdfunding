package com.crux.crowd.common.util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * 筹钱吧项目统一工具类
 * @author crux
 * @version 2022/03/10
 */
public final class CrowdUtils{
	private CrowdUtils(){}

	/**
	 * 判断请求是否是JSON请求
	 * @param request 请求域{@link HttpServletRequest}。
	 * @return 如果是JSON请求，返回true
	 */
	public static boolean isJSONRequest(HttpServletRequest request){
		Optional<String> accept = Optional.ofNullable(request.getHeader("Accept"));
		Optional<String> xRequestedWith = Optional.ofNullable(request.getHeader("X-Requested-With"));
		return accept.map(CrowdConstant.HttpAttribute.APP_JSON::contains).orElse(false) ||
				xRequestedWith.map(CrowdConstant.HttpAttribute.APP_XML_HTTP_REQUEST::equals).orElse(false);
	}

	/**
	 * 将字符串使用MD5加密
	 * @param str 被加密的原字符串
	 * @return 加密后的字符串
	 * @throws IllegalArgumentException 如果字符串为空
	 */
	public static String md5Encryption(String str){
		Optional.ofNullable(str).filter(s -> !s.isEmpty()).orElseThrow(() -> new IllegalArgumentException("字符串不能为空！！！"));
		try{
			String md5Algorithm = "MD5";
			byte[] input = str.getBytes(StandardCharsets.UTF_8);
			byte[] output = MessageDigest.getInstance(md5Algorithm).digest(input);
			return new BigInteger(1, output).toString(16).toUpperCase();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return null;
	}
}

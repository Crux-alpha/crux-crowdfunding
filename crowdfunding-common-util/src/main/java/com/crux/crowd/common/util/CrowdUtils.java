package com.crux.crowd.common.util;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 众筹平台项目统一工具类
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

	/**
	 * 将VO转成PO<br/>
	 * 根据同名属性转换
	 * @param entityVO VO实体
	 * @param <P> PO实体类型
	 * @return PO实体
	 * @throws ReflectiveOperationException 如果没有setter和getter，或者方法具有无法访问的权限，或者PO没有无参数构造方法
	 */
	public static <P> P entityTransition(Object entityVO, Class<P> POClass) throws ReflectiveOperationException{
		Method[] VOMethods = entityVO.getClass().getMethods();
		Method[] POMethods = POClass.getMethods();

		P entityPO = POClass.getConstructor().newInstance();
		Map<String,Method> VOSetter = new HashMap<>();
		Map<String,Method> VOGetter = new HashMap<>();

		for(Method method : VOMethods){
			String name = method.getName();

			if(name.startsWith("set")){
				VOSetter.put(name, method);
			}else if(name.startsWith("get")){
				VOGetter.put(name, method);
			}
		}

		for(Method method : POMethods){
			String name = method.getName();

			if(VOSetter.containsKey(name)){
				Method getter = VOGetter.get(name.replace("set", "get"));
				method.invoke(entityPO, getter.invoke(entityVO));
			}
		}
		return entityPO;
	}
}

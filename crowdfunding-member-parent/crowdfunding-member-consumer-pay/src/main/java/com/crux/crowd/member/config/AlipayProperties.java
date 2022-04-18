package com.crux.crowd.member.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 支付宝支付接口配置信息
 * @since 2022-04-18
 */
@Getter
@Setter
@ConfigurationProperties("alipay.config")
public class AlipayProperties{

	/**
	 * 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	 */
	private String appId;
	/**
	 * 商户私钥，您的PKCS8格式RSA2私钥
	 */
	private String merchantPrivateKey;
	/**
	 * 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥
	 */
	private String alipayPublicKey;
	/**
	 * 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	 */
	private String notifyUrl;
	/**
	 * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	 */
	private String returnUrl;
	/**
	 * 签名方式
	 */
	private String signType = "RSA2";
	/**
	 * 字符编码格式
	 */
	private String charset = "utf-8";
	/**
	 * 支付宝网关
	 */
	private String gatewayUrl;
	private String logPath = "C:\\";
}

package com.crux.crowd.member.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.crux.crowd.member.config.AlipayProperties;
import org.springframework.stereotype.Component;

@Component
public class AlipayApiProvider{

	private final AlipayProperties alipayProperties;

	public AlipayApiProvider(AlipayProperties alipayProperties){
		this.alipayProperties = alipayProperties;
	}


	/**
	 * 进入支付宝[沙箱]提供的订单页面
	 * @param orderNum 订单号，和商家提供的订单号一致
	 * @param totalAmount 总金额
	 * @param subject 订单标题
	 * @param body 订单说明
	 * @return HTML字符串形式的页面
	 * @throws AlipayApiException 如果出现任何调用支付宝API产生的问题
	 */
	public String alipayOrderPage(String orderNum, String totalAmount, String subject, String body) throws AlipayApiException{
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(
				alipayProperties.getGatewayUrl(),
				alipayProperties.getAppId(),
				alipayProperties.getMerchantPrivateKey(),
				"json",
				alipayProperties.getCharset(),
				alipayProperties.getAlipayPublicKey(),
				alipayProperties.getSignType());

		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(alipayProperties.getReturnUrl());
		alipayRequest.setNotifyUrl(alipayProperties.getNotifyUrl());

		alipayRequest.setBizContent("{\"out_trade_no\":\""+ orderNum +"\","
				+ "\"total_amount\":\""+ totalAmount +"\","
				+ "\"subject\":\""+ subject +"\","
				+ "\"body\":\""+ body +"\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

		//若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
		//alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
		//		+ "\"total_amount\":\""+ total_amount +"\","
		//		+ "\"subject\":\""+ subject +"\","
		//		+ "\"body\":\""+ body +"\","
		//		+ "\"timeout_express\":\"10m\","
		//		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		//请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

		//请求
		return alipayClient.pageExecute(alipayRequest).getBody();
	}
}

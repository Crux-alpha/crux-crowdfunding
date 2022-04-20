package com.crux.crowd.member.api;

import com.alipay.api.AlipayApiException;

import java.util.Map;

/**
 * 支付宝远程接口
 * @since 2022-04-19
 */
public interface AlipayRemoteService{

    /**
     * 进入支付宝[沙箱]提供的订单页面
     * @param orderNum 订单号，和商家提供的订单号一致
     * @param totalAmount 总金额
     * @param subject 订单标题
     * @param body 订单说明
     * @return HTML字符串形式的页面
     * @throws AlipayApiException 如果出现任何调用支付宝API产生的问题
     */
    String alipayOrderPage(String orderNum, String totalAmount, String subject, String body) throws AlipayApiException;

    /**
     * 验证签名
     * @param requestParameterMap 将请求对象的所有参数传递过来：request.getParameterMap()
     * @return 验证结果
     * @throws AlipayApiException 如果出现任何调用支付宝API产生的问题
     */
    boolean signVerified(Map<String,String[]> requestParameterMap) throws AlipayApiException;
}

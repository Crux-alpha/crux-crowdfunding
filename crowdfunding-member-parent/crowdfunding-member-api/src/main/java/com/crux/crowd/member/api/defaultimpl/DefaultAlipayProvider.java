package com.crux.crowd.member.api.defaultimpl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.crux.crowd.member.api.AlipayProperties;
import com.crux.crowd.member.api.AlipayRemoteService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service("alipayRemoteService")
@ConditionalOnBean(AlipayProperties.class)
public class DefaultAlipayProvider implements AlipayRemoteService{

    private final AlipayProperties alipayProperties;

    public DefaultAlipayProvider(AlipayProperties alipayProperties){
        this.alipayProperties = alipayProperties;
    }


    @Override
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

    @Override
    public boolean signVerified(Map<String,String[]> requestParameterMap) throws AlipayApiException{
        // 获取支付宝GET过来反馈信息

        /* OLD CODE:
        Map<String,String> params = new HashMap<>();
        for(String name : requestParameterMap.keySet()){
            String[] values = requestParameterMap.get(name);
            // 旧的字符序列拼接方式
            String valueStr = "";
            for(int i = 0; i < values.length; i++){
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 新的字符序列拼接方式：String value = String.join(",", values);
            // 乱码解决，这段代码在出现乱码时使用
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }*/

        // JAVA1.8: Stream + StringJoiner
        Map<String,String> params = requestParameterMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> String.join(",", entry.getValue())));

        //调用SDK验证签名
        return AlipaySignature.rsaCheckV1(
                params,
                alipayProperties.getAlipayPublicKey(),
                alipayProperties.getCharset(),
                alipayProperties.getSignType());

    }


}

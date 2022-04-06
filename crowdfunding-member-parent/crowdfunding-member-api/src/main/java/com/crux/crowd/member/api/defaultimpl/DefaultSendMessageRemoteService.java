package com.crux.crowd.member.api.defaultimpl;

import com.crux.crowd.common.util.HttpUtils;
import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.api.MessageProviderProperties;
import com.crux.crowd.member.api.SendMessageRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("sendMessageRemoteService")
@EnableConfigurationProperties(MessageProviderProperties.class)
public class DefaultSendMessageRemoteService implements SendMessageRemoteService{

	private MessageProviderProperties properties;

	/**
	 * 发送短信验证码
	 * @param phone 手机号
	 * @param code 验证码
	 * @param minutes 有效时长
	 * @return 执行结果
	 */
	@Override
	public ResultEntity<String,String> sendMessage(String phone, String code, int minutes){
		if(phone.length() != 11) return ResultEntity.failure("手机号码格式不正确");

		String host = properties.getHost();
		String path = properties.getPath();
		String method = properties.getMethod();

		Map<String,String> headers = properties.getHeaders();

		Map<String,String> queries = new HashMap<>();
		queries.putAll(properties.getSign());
		queries.putAll(properties.getTemplate());
		queries.put(properties.getMobileName(), phone);
		String paramValue = properties.getParam().getCodePrefix() + code + ',' + properties.getParam().getMinutePrefix() + minutes;
		queries.put(properties.getParam().getParamName(), paramValue);

		Map<String,String> bodies = properties.getBodies();

		try{
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, queries, bodies);
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == 200) return ResultEntity.success(Collections.singletonMap("code", code));

			String result = response.toString();
			String message = Arrays.stream(result.split(", ")).skip(2).filter(s -> s.contains("X-Ca-Error-Message:")).map(s -> s.split(": ")[1])
					.findFirst().orElse("未知错误，请稍后重试");
			return ResultEntity.failure(message);
		}catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
			return ResultEntity.failure("发送失败！原因：" + e.getMessage());
		}
	}

	@Autowired
	public void setProperties(MessageProviderProperties properties){
		this.properties = properties;
	}
}

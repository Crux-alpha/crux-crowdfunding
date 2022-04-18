package com.crux.crowd.member.controller;

import com.alipay.api.AlipayApiException;
import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.member.entity.vo.OrderProjectVO;
import com.crux.crowd.member.entity.vo.OrderVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.crux.crowd.common.util.CrowdConstant.NUMBER_TIME_FORMAT;
import static com.crux.crowd.common.util.CrowdConstant.SESSION_ATTRIBUTE_ORDER_PROJECT;

/**
 *
 */
@RestController
public class PayController{

	private final AlipayApiProvider alipayApiProvider;

	public PayController(AlipayApiProvider alipayApiProvider){
		this.alipayApiProvider = alipayApiProvider;
	}


	/**
	 * <h3>支持页面步骤3</h3>
	 * 立即结账，将数据收集并生成订单，返回由支付宝api生成的订单页面
	 * @param orderVO 当前订单信息
	 * @throws AlipayApiException 如果出现任何调用支付宝API产生的问题
	 */
	@PostMapping("/support_project/order")
	public String generateOrder(OrderVO orderVO, HttpSession session) throws AlipayApiException{
		Object orderProject = session.getAttribute(SESSION_ATTRIBUTE_ORDER_PROJECT);
		if(orderProject instanceof OrderProjectVO){
			OrderProjectVO orderProjectVO = (OrderProjectVO) orderProject;
			// 1、保存项目信息
			orderVO.setOrderProjectVO(orderProjectVO);
			/*
				2、生成订单号，格式为：
					当前时间年月日时分秒 + UUID去掉中间'-'作为用户id
			 */
			String instant = LocalDateTime.now().format(NUMBER_TIME_FORMAT);
			String uid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
			orderVO.setOrderNum(instant + uid);
			// 3、调用api，生成订单页面
			return alipayApiProvider.alipayOrderPage(orderVO.getOrderNum(), orderVO.getOrderAmount().toString(), orderProjectVO.getProjectName(), orderProjectVO.getReturnContent());
		}else return CrowdConstant.TipsMessage.HTML_FAILURE;
	}
}
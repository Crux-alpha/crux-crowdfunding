package com.crux.crowd.member.controller;

import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.entity.vo.OrderProjectVO;
import com.crux.crowd.member.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/order")
public class OrderProviderController{

	private final OrderService orderService;

	public OrderProviderController(OrderService orderService){
		this.orderService = orderService;
	}

	/**
	 * 根据回报ID查询当前订单的项目确认信息
	 * @param returnId 回报id
	 * @return {@link OrderProjectVO}
	 */
	@GetMapping("/pay/{returnId}")
	public ResultEntity<String,OrderProjectVO> getOrderProject(@PathVariable("returnId") Integer returnId){
		OrderProjectVO orderProject = orderService.getOrderProjectVO(returnId);
		if(orderProject == null) return ResultEntity.failure(CrowdConstant.TipsMessage.DATA_NOT_FOUNT);
		return ResultEntity.success(Collections.singletonMap("orderProject", orderProject));
	}
}
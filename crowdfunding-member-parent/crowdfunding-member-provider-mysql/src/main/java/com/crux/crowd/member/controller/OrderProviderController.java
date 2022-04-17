package com.crux.crowd.member.controller;

import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.entity.vo.AddressVO;
import com.crux.crowd.member.entity.vo.OrderProjectVO;
import com.crux.crowd.member.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

	/**
	 * 根据用户id查询所有收货地址
	 * @param memberId 用户id
	 * @return 返回一个包含Map的响应实体，key为addressId，value为addressVO
	 */
	@GetMapping("/pay/address/{memberId}")
	public ResultEntity<Integer,AddressVO> getAddresses(@PathVariable("memberId") Integer memberId){
		return ResultEntity.success(orderService.mapAddressVO(memberId));
	}

	@PostMapping("/pay/address/save")
	public ResultEntity<Integer,AddressVO> saveAndGetAddress(@RequestBody AddressVO addressVO){
		if(!orderService.saveAddress(addressVO)) return ResultEntity.error(CrowdConstant.TipsMessage.SERVER_ERROR);
		return getAddresses(addressVO.getMemberId());
	}
}
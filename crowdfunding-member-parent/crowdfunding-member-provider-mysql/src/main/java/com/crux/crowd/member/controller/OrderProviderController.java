package com.crux.crowd.member.controller;

import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.entity.vo.AddressVO;
import com.crux.crowd.member.entity.vo.OrderProjectVO;
import com.crux.crowd.member.entity.vo.OrderVO;
import com.crux.crowd.member.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.crux.crowd.common.util.CrowdConstant.TipsMessage.*;

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
		if(orderProject == null) return ResultEntity.failure(DATA_NOT_FOUNT);
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
		orderService.saveAddress(addressVO);
		return getAddresses(addressVO.getMemberId());
	}

	@PostMapping
	public ResultEntity<?,?> saveOrder(@RequestBody OrderVO orderVO){
		orderService.saveOrder(orderVO);
		return ResultEntity.success("保存成功");
	}

	@DeleteMapping("/{orderNum}")
	public ResultEntity<?,?> removeOrder(@PathVariable("orderNum") String orderNum, @RequestParam("memberId") Integer memberId){
		return orderService.removeByOrderNumAndMemberId(orderNum, memberId) ?
				ResultEntity.success("删除成功") : ResultEntity.failure("删除失败");
	}

	@GetMapping("/{orderNum}")
	ResultEntity<String,OrderVO> getOrder(@PathVariable("orderNum") String orderNum){
		return Optional.ofNullable(orderService.getOrder(orderNum))
				.map(o -> ResultEntity.success(Collections.singletonMap("orderVO", o))).orElse(ResultEntity.failure(DATA_NOT_FOUNT));
	}
}
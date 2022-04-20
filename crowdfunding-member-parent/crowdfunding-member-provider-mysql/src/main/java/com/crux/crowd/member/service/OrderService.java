package com.crux.crowd.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.crowd.member.entity.po.OrderPO;
import com.crux.crowd.member.entity.po.OrderProjectPO;
import com.crux.crowd.member.entity.vo.AddressVO;
import com.crux.crowd.member.entity.vo.OrderProjectVO;
import com.crux.crowd.member.entity.vo.OrderVO;

import java.util.Map;

public interface OrderService extends IService<OrderPO>{

	/**
	 * 根据回报id查询当前订单的项目信息
	 * @param returnId 回报id
	 * @return 订单的项目视图信息
	 */
	OrderProjectVO getOrderProjectVO(Integer returnId);

	/**
	 * 获取用户的收货地址
	 * @param memberId 用户id
	 * @return 所有收货地址。key为addressId，value为address。如果没有，返回空图
	 */
	Map<Integer,AddressVO> mapAddressVO(Integer memberId);

	/**
	 * 保存一个收货地址
	 * @param addressVO 一个收货地址，必须包含memberId
	 */
	void saveAddress(AddressVO addressVO);

	/**
	 * 保存一个订单，需要已经
	 * @param orderVO 订单详情
	 */
	void saveOrderVO(OrderVO orderVO);

	/**
	 * 支付订单
	 * @param orderNum 订单号
	 * @param payOrderNum 支付宝交易号
	 * @param orderAmount 订单金额
	 */
	void payOrder(String orderNum, String payOrderNum, double orderAmount);

	/**
	 * 根据订单号获取订单
	 * @param orderNum 订单号
	 * @return 该订单的详细信息
	 */
	OrderProjectPO getOrderProjectPO(String orderNum);


	void removeOrder(String orderNum);

}

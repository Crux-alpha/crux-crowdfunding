package com.crux.crowd.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.crowd.member.entity.po.OrderPO;
import com.crux.crowd.member.entity.vo.AddressVO;
import com.crux.crowd.member.entity.vo.OrderProjectVO;

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
	 * @return 执行结果
	 */
	boolean saveAddress(AddressVO addressVO);
}

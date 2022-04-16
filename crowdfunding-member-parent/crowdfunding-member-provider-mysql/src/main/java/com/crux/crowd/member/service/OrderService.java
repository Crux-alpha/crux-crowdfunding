package com.crux.crowd.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.crowd.member.entity.po.OrderPO;
import com.crux.crowd.member.entity.vo.OrderProjectVO;

public interface OrderService extends IService<OrderPO>{

	/**
	 * 根据回报id查询当前订单的项目信息
	 * @param returnId 回报id
	 * @return 订单的项目视图信息
	 */
	OrderProjectVO getOrderProjectVO(Integer returnId);
}

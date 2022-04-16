package com.crux.crowd.member.service.impl;

import com.crux.crowd.member.entity.po.OrderPO;
import com.crux.crowd.member.entity.vo.OrderProjectVO;
import com.crux.crowd.member.mapper.OrderPOMapper;
import com.crux.crowd.member.mapper.OrderProjectPOMapper;
import com.crux.crowd.member.service.AbstractService;
import com.crux.crowd.member.service.OrderService;
import com.crux.crowd.member.service.ServiceException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service("orderService")
public class OrderServiceImpl extends AbstractService<OrderPOMapper,OrderPO> implements OrderService{

	private final OrderProjectPOMapper opMapper;

	public OrderServiceImpl(OrderProjectPOMapper opMapper){
		this.opMapper = opMapper;
	}

	@Override
	public OrderProjectVO getOrderProjectVO(Integer returnId){
		return opMapper.selectOrderProjectVO(returnId);
	}

	@Override
	protected <R> R execute(Supplier<R> method) throws ServiceException{
		return method.get();
	}
}

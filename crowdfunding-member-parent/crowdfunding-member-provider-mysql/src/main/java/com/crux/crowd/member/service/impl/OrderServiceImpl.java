package com.crux.crowd.member.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.crux.crowd.member.entity.po.AddressPO;
import com.crux.crowd.member.entity.po.OrderPO;
import com.crux.crowd.member.entity.vo.AddressVO;
import com.crux.crowd.member.entity.vo.OrderProjectVO;
import com.crux.crowd.member.mapper.AddressPOMapper;
import com.crux.crowd.member.mapper.OrderPOMapper;
import com.crux.crowd.member.mapper.OrderProjectPOMapper;
import com.crux.crowd.member.service.AbstractService;
import com.crux.crowd.member.service.OrderService;
import com.crux.crowd.member.service.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service("orderService")
public class OrderServiceImpl extends AbstractService<OrderPOMapper,OrderPO> implements OrderService{

	private final OrderProjectPOMapper opMapper;
	private final AddressPOMapper addressMapper;

	public OrderServiceImpl(OrderProjectPOMapper opMapper, AddressPOMapper addressMapper){
		this.opMapper = opMapper;
		this.addressMapper = addressMapper;
	}

	@Override
	public OrderProjectVO getOrderProjectVO(Integer returnId){
		return opMapper.selectOrderProjectVO(returnId);
	}

	@Override
	public Map<Integer,AddressVO> mapAddressVO(Integer memberId){
		List<AddressPO> addressPOList =
				Optional.ofNullable(addressMapper.selectList(Wrappers.<AddressPO>lambdaQuery().eq(AddressPO::getMemberId, memberId)))
						.orElseGet(ArrayList::new);
		Collections.reverse(addressPOList);
		return addressPOList.stream().collect(Collectors.toMap(AddressPO::getId, AddressVO::new));
	}

	public boolean saveAddress(AddressVO addressVO){
		return execute(() -> addressVO.getMemberId() != null && SqlHelper.retBool(addressMapper.insert(new AddressPO(addressVO))));
	}

	@Override
	protected <R> R execute(Supplier<R> method) throws ServiceException{
		return method.get();
	}
}

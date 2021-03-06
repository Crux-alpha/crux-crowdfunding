package com.crux.crowd.member.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.crux.crowd.member.entity.po.AddressPO;
import com.crux.crowd.member.entity.po.OrderPO;
import com.crux.crowd.member.entity.po.OrderProjectPO;
import com.crux.crowd.member.entity.vo.AddressVO;
import com.crux.crowd.member.entity.vo.OrderProjectVO;
import com.crux.crowd.member.entity.vo.OrderVO;
import com.crux.crowd.member.mapper.AddressPOMapper;
import com.crux.crowd.member.mapper.OrderPOMapper;
import com.crux.crowd.member.mapper.OrderProjectPOMapper;
import com.crux.crowd.member.service.AbstractService;
import com.crux.crowd.member.service.OrderService;
import com.crux.crowd.member.service.ServiceException;
import org.springframework.stereotype.Service;
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
		return opMapper.selectOrderProjectVOByReturnId(returnId);
	}

	@Override
	public Map<Integer,AddressVO> mapAddressVO(Integer memberId){
		List<AddressPO> addressPOList =
				Optional.ofNullable(
						addressMapper.selectList(Wrappers.<AddressPO>lambdaQuery().eq(AddressPO::getMemberId, memberId).orderByDesc(AddressPO::getId))
						).orElseGet(ArrayList::new);
		return addressPOList.stream().collect(Collectors.toMap(AddressPO::getId, AddressVO::new));
	}

	public void saveAddress(AddressVO addressVO){
		execute(() -> addressVO.getMemberId() != null && SqlHelper.retBool(addressMapper.insert(new AddressPO(addressVO))));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveOrder(OrderVO orderVO){
		final OrderProjectPO orderProjectPO = new OrderProjectPO(orderVO.getOrderProjectVO());
		final OrderPO orderPO = new OrderPO(orderVO);

		execute(() -> {
			if(save(orderPO)){
				orderProjectPO.setOrderId(orderPO.getId());
				return SqlHelper.retBool(opMapper.insert(orderProjectPO));
			}
			return false;
		});
	}

	@Override
	public OrderProjectPO getOrderProjectPO(String orderNum){
		return opMapper.selectByOrderNum(orderNum);
	}

	@Override
	public boolean removeByOrderNumAndMemberId(String orderNum, Integer memberId){
		OrderPO orderPO = getOne(lambdaQueryWrapper().eq(OrderPO::getOrderNum, orderNum));
		if(orderPO == null) return false;
		AddressPO address = addressMapper.selectOne(Wrappers.<AddressPO>lambdaQuery().eq(AddressPO::getId, orderPO.getAddressId()).eq(AddressPO::getMemberId, memberId));
		if(address == null) return false;
		Integer id = orderPO.getId();
		return removeById(id) &&
				SqlHelper.retBool(opMapper.delete(Wrappers.<OrderProjectPO>lambdaQuery().eq(OrderProjectPO::getOrderId, id)));
	}

	@Override
	public OrderVO getOrder(String orderNum){
		OrderPO orderPO = getOne(lambdaQueryWrapper().eq(OrderPO::getOrderNum, orderNum));
		OrderVO orderVO = null;

		if(orderPO != null){
			OrderProjectPO orderProjectPO = opMapper.selectOne(Wrappers.<OrderProjectPO>lambdaQuery().eq(OrderProjectPO::getOrderId, orderPO.getId()));

			orderVO = new OrderVO(orderPO.getAddressId(), orderPO.getInvoice(), orderPO.getInvoiceTitle(), orderPO.getOrderRemark());
			orderVO.setOrderNum(orderNum);
			orderVO.setPayOrderNum(orderPO.getPayOrderNum());
			orderVO.setOrderAmount(orderPO.getOrderAmount());

			OrderProjectVO orderProjectVO = new OrderProjectVO(orderProjectPO.getProjectName(),
					orderProjectPO.getLaunchName(),
					orderProjectPO.getReturnContent(),
					orderProjectPO.getReturnCount(),
					orderProjectPO.getSupportPrice(),
					orderProjectPO.getFreight(),
					orderProjectPO.getOrderId(),
					null, null, orderProjectPO.getReturnId());
			orderVO.setOrderProjectVO(orderProjectVO);
		}

		return orderVO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeBatchByIds(Collection<?> list){
		return super.removeBatchByIds(list) &&
				SqlHelper.retBool(opMapper.delete(Wrappers.<OrderProjectPO>lambdaQuery().in(OrderProjectPO::getOrderId, list)));
	}

	@Override
	protected <R> R execute(Supplier<R> method) throws ServiceException{
		R r = method.get();
		if(r instanceof Boolean && !((Boolean)r)) throw new ServiceException("??????????????????");
		return r;
	}
}

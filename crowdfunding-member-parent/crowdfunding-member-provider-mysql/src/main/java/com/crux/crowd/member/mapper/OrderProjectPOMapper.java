package com.crux.crowd.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crux.crowd.member.entity.po.OrderProjectPO;
import com.crux.crowd.member.entity.vo.OrderProjectVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderProjectPOMapper extends BaseMapper<OrderProjectPO>{

	OrderProjectVO selectOrderProjectVOByReturnId(Integer returnId);

	OrderProjectPO selectOrderProjectPOByOrderNum(String orderNum);
}

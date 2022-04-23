package com.crux.crowd.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crux.crowd.member.entity.po.MemberPO;
import com.crux.crowd.member.entity.vo.MemberSupportProjectVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberPOMapper extends BaseMapper<MemberPO>{
}

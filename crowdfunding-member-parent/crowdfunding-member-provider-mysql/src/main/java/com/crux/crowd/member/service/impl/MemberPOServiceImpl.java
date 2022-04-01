package com.crux.crowd.member.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crux.crowd.member.entity.po.MemberPO;
import com.crux.crowd.member.mapper.MemberPOMapper;
import com.crux.crowd.member.service.MemberPOService;
import org.springframework.stereotype.Service;

@Service("memberPOService")
public class MemberPOServiceImpl extends ServiceImpl<MemberPOMapper,MemberPO> implements MemberPOService{
}

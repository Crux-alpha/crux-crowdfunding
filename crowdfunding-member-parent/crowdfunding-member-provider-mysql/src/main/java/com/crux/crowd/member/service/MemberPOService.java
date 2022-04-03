package com.crux.crowd.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.crowd.member.entity.po.MemberPO;

public interface MemberPOService extends IService<MemberPO>{

	MemberPO getByLoginAcct(String loginAcct);
}

package com.crux.crowd.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.crowd.member.entity.po.MemberPO;

public interface MemberService extends IService<MemberPO>{

	MemberPO getByLoginAcctOrPhone(String accountOrPhone);
}

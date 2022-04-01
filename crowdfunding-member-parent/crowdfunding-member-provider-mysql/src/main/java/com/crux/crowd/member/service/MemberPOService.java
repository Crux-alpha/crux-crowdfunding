package com.crux.crowd.member.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.crowd.member.entity.po.MemberPO;

public interface MemberPOService extends IService<MemberPO>{

	default MemberPO getByLoginAcct(String loginAcct){
		return getOne(Wrappers.<MemberPO>lambdaQuery().eq(MemberPO::getLoginAcct, loginAcct));
	}
}

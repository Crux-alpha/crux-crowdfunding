package com.crux.crowd.member.api;

import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.entity.po.MemberPO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("crowd-mysql")
public interface DataSourceRemoteService{

	@RequestMapping(path = "/member/get", params = "loginAcct")
	ResultEntity<String,MemberPO> getMemberByLoginAcct(@RequestParam("loginAcct") String loginAcct);
}

package com.crux.crowd.member.controller;

import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.entity.po.MemberPO;
import com.crux.crowd.member.service.MemberPOService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static java.util.Collections.singletonMap;

@Slf4j
@RestController
public class MemberPOProviderController{

	private MemberPOService memberPOService;

	@GetMapping(path = "/member", params = "login_acct")
	public ResultEntity<String,MemberPO> getMemberByLoginAcct(@RequestParam("login_acct") String loginAcct){
		try{
			MemberPO memberPO = memberPOService.getByLoginAcct(loginAcct);
			return ResultEntity.success(singletonMap("memberPO", memberPO));
		}catch(Exception e){
			log.warn(e.getMessage());
			return ResultEntity.failure("查询失败。原因：" + e.getMessage());
		}
	}

	@PostMapping("/member")
	public ResultEntity<?,?> saveMember(@RequestBody MemberPO memberPO){
		memberPOService.save(memberPO);
		return ResultEntity.success("保存成功");
	}

	@Autowired
	public void setMemberPOService(MemberPOService memberPOService){
		this.memberPOService = memberPOService;
	}
}

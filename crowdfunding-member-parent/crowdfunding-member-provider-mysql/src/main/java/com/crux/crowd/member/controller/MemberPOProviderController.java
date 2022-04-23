package com.crux.crowd.member.controller;

import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.entity.po.MemberPO;
import com.crux.crowd.member.entity.vo.MemberSupportProjectVO;
import com.crux.crowd.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
public class MemberPOProviderController{

	private MemberService memberService;

	@GetMapping(path = "/member", params = "account_or_phone")
	public ResultEntity<String,MemberPO> getMemberByLoginAcct(@RequestParam("account_or_phone") String account){
		MemberPO memberPO = memberService.getByLoginAcctOrPhone(account);
		return Optional.ofNullable(memberPO).map(m -> ResultEntity.success(Collections.singletonMap("memberPO", m)))
				.orElse(ResultEntity.failure("没有查询到数据"));
	}

	@PostMapping("/member")
	public ResultEntity<?,?> saveMember(@RequestBody MemberPO memberPO){
		memberService.save(memberPO);
		return ResultEntity.success("保存成功");
	}

	@Autowired
	public void setMemberPOService(MemberService memberService){
		this.memberService = memberService;
	}
}

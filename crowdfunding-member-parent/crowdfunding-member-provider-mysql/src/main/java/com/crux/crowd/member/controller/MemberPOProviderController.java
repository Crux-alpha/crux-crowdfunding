package com.crux.crowd.member.controller;

import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.entity.po.MemberPO;
import com.crux.crowd.member.service.MemberPOService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
public class MemberPOProviderController{

	private MemberPOService memberPOService;

	@GetMapping(path = "/member", params = "account_or_phone")
	public ResultEntity<String,MemberPO> getMemberByLoginAcct(@RequestParam("account_or_phone") String account){
		MemberPO memberPO = memberPOService.getByLoginAcctOrPhone(account);
		return Optional.ofNullable(memberPO).map(m -> ResultEntity.success(Collections.singletonMap("memberPO", m)))
				.orElse(ResultEntity.failure("没有查询到数据"));
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

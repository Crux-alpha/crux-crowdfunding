package com.crux.crowd.member.api;

import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.entity.po.MemberPO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 数据源服务接口
 */
@FeignClient("crowd-mysql")
public interface DataSourceRemoteService{

	/**
	 * 通过账号获取会员信息
	 * @param loginAcct 会员账号
	 * @return 执行结果
	 */
	@GetMapping(path = "/member", params = "login_acct")
	ResultEntity<String,MemberPO> getMemberByLoginAcct(@RequestParam("login_acct") String loginAcct);

	/**
	 * 保存会员
	 */
	@PostMapping("/member")
 	ResultEntity<?,?> saveMember(@RequestBody MemberPO memberPO);
}

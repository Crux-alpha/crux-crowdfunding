package com.crux.crowd.member.api;

import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.entity.po.MemberPO;
import com.crux.crowd.member.entity.vo.PortalTypeVO;
import com.crux.crowd.member.entity.vo.ProjectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据源服务接口
 */
@FeignClient("crowd-mysql")
public interface DataSourceRemoteService{

	/**
	 * 通过账号或手机号获取会员信息
	 * @param account 会员账号或手机号
	 * @return 执行结果
	 */
	@GetMapping(path = "/member", params = "account_or_phone")
	ResultEntity<String,MemberPO> getMemberByLoginAcctOrPhone(@RequestParam("account_or_phone") String account);

	/**
	 * 保存会员
	 */
	@PostMapping("/member")
 	ResultEntity<?,?> saveMember(@RequestBody MemberPO memberPO);

	/**
	 * 保存发起项目
	 */
	@PostMapping("/project")
	ResultEntity<?,?> saveProject(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId);

	/**
	 * 获取首页显示的项目数据
	 */
	@GetMapping("/index/portal_project")
	ResultEntity<String,List<PortalTypeVO>> getPortalProjectAll();
}

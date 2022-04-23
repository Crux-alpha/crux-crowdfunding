package com.crux.crowd.member.controller;

import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.entity.po.OrderProjectPO;
import com.crux.crowd.member.entity.vo.*;
import com.crux.crowd.member.service.OrderService;
import com.crux.crowd.member.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class ProjectProviderController{

	private final OrderService orderService;
	private final ProjectService projectService;

	public ProjectProviderController(OrderService orderService, ProjectService projectService){
		this.orderService = orderService;
		this.projectService = projectService;
	}

	@PostMapping("/project")
	public ResultEntity<?,?> saveProject(@RequestBody ProjectVO projectVO){
		projectService.saveProject(projectVO);
		return ResultEntity.success("保存成功");
	}

	@GetMapping("/index/portal_project")
	public ResultEntity<String,List<PortalTypeVO>> getPortalProjectAll(){
		List<PortalTypeVO> portalTypeList = projectService.listPortalProject();
		return ResultEntity.success(Collections.singletonMap(CrowdConstant.PORTAL_PROJECT_LIST, portalTypeList));
	}

	@GetMapping("/project/detail/{id}")
	public ResultEntity<String,DetailProjectVO> getDetailProject(@PathVariable("id") Integer id){
		DetailProjectVO detailProject = projectService.getDetailProjectById(id);
		ResultEntity<String,DetailProjectVO> result;
		if(detailProject == null) result = ResultEntity.failure("没有查询到id为"+ id +"的项目");
		else result = ResultEntity.success(Collections.singletonMap(CrowdConstant.DETAIL_PROJECT, detailProject));
		return result;
	}

	@PutMapping("/project/support/{orderNum}")
	public ResultEntity<?,?> supportProject(@PathVariable("orderNum") String orderNum,
											@RequestParam("supportMoney") double supportMoney){
		OrderProjectPO orderProjectPO = orderService.getOrderProjectPO(orderNum);
		if(orderProjectPO == null) return ResultEntity.error("没有查询到订单号为" + orderNum + "的订单");
		projectService.supportProject(orderProjectPO);
		return ResultEntity.success("保存成功");
	}

	@GetMapping("/project/member/support/{memberId}")
	public ResultEntity<String,List<MemberSupportProjectVO>> getMemberSupportProject(@PathVariable("memberId") Integer memberId){
		return ResultEntity.success(Collections.singletonMap("listMemberSupportProjectVO", projectService.listMemberSupportProject(memberId)));
	}

	@GetMapping("/project/member/project/{memberId}")
	public ResultEntity<String,List<MemberProjectVO>> getMemberProject(@PathVariable("memberId") Integer memberId){
		return ResultEntity.success(Collections.singletonMap("listMemberProjectVO", projectService.listMemberProject(memberId)));
	}

	@DeleteMapping("/project/{id}")
	public ResultEntity<?,?> removeProject(@PathVariable("id") Integer id, @RequestParam("memberId") Integer memberId){
		return projectService.removeByIdAndMemberId(id, memberId) ? ResultEntity.success("删除成功") : ResultEntity.failure("删除失败");
	}
}

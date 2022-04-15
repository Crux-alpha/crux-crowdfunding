package com.crux.crowd.member.controller;

import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.entity.vo.DetailProjectVO;
import com.crux.crowd.member.entity.vo.PortalTypeVO;
import com.crux.crowd.member.entity.vo.ProjectVO;
import com.crux.crowd.member.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class ProjectProviderController{

	private final ProjectService projectService;

	public ProjectProviderController(ProjectService projectService){
		this.projectService = projectService;
	}

	@PostMapping("/project")
	public ResultEntity<?,?> saveProject(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId){
		projectService.saveProject(projectVO, memberId);
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
}

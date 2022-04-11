package com.crux.crowd.member.controller;

import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.entity.vo.ProjectVO;
import com.crux.crowd.member.service.ProjectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

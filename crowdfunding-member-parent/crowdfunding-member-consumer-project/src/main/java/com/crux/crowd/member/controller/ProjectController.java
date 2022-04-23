package com.crux.crowd.member.controller;

import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.common.util.ResponseResult;
import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.api.DataSourceRemoteService;
import com.crux.crowd.member.entity.vo.DetailProjectVO;
import com.crux.crowd.member.entity.vo.MemberInfoVO;
import com.crux.crowd.member.entity.vo.MemberProjectVO;
import com.crux.crowd.member.entity.vo.MemberSupportProjectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.crux.crowd.common.util.CrowdConstant.SESSION_ATTRIBUTE_MEMBER_INFO;

@Slf4j
@Controller
public class ProjectController{

	private final DataSourceRemoteService dataSourceRemoteService;

	public ProjectController(DataSourceRemoteService dataSourceRemoteService){
		this.dataSourceRemoteService = dataSourceRemoteService;
	}

	/**
	 * 项目详情页
	 * @param id 项目id
	 * @param mav 视图与模型
	 * @return mav
	 */
	@RequestMapping("/detail/{id}")
	public ModelAndView project(@PathVariable("id") Integer id, ModelAndView mav){
		ResultEntity<String,DetailProjectVO> detailProject = dataSourceRemoteService.getDetailProject(id);
		if(ResponseResult.SUCCESS.equalsResultEntity(detailProject)){
			mav.setViewName("project/project");
			mav.addObject("project", detailProject.getData().get(CrowdConstant.DETAIL_PROJECT));
		}else{
			mav.setViewName("error");
			mav.setStatus(HttpStatus.NOT_FOUND);
			mav.addObject("message", detailProject.getMessage());
		}
		return mav;
	}

	@DeleteMapping("/remove/{id}")
	@ResponseBody
	public ResultEntity<?,?> remove(@PathVariable("id") Integer id, HttpSession session){
		Object memberInfo = session.getAttribute(SESSION_ATTRIBUTE_MEMBER_INFO);
		if(memberInfo instanceof MemberInfoVO){
			return dataSourceRemoteService.removeProject(id, ((MemberInfoVO)memberInfo).getId());
		}
		return ResultEntity.failure(CrowdConstant.TipsMessage.HTML_FAILURE);
	}
}

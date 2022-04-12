package com.crux.crowd.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.crowd.member.entity.po.ProjectPO;
import com.crux.crowd.member.entity.vo.DetailProjectVO;
import com.crux.crowd.member.entity.vo.PortalTypeVO;
import com.crux.crowd.member.entity.vo.ProjectVO;

import java.util.List;

public interface ProjectService extends IService<ProjectPO>{

	void saveProject(ProjectVO projectVO, Integer memberId);

	List<PortalTypeVO> listPortalProject();

	DetailProjectVO getDetailProjectById(Integer id);
}

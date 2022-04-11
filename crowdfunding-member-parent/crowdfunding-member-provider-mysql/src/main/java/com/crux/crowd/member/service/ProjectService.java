package com.crux.crowd.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.crowd.member.entity.po.ProjectPO;
import com.crux.crowd.member.entity.vo.ProjectVO;

public interface ProjectService extends IService<ProjectPO>{

	void saveProject(ProjectVO projectVO, Integer memberId);
}

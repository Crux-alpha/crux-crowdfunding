package com.crux.crowd.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.crowd.member.entity.po.OrderProjectPO;
import com.crux.crowd.member.entity.po.ProjectPO;
import com.crux.crowd.member.entity.vo.DetailProjectVO;
import com.crux.crowd.member.entity.vo.OrderProjectVO;
import com.crux.crowd.member.entity.vo.PortalTypeVO;
import com.crux.crowd.member.entity.vo.ProjectVO;

import java.util.List;

public interface ProjectService extends IService<ProjectPO>{


	void saveProject(ProjectVO projectVO, Integer memberId);

	List<PortalTypeVO> listPortalProject();

	DetailProjectVO getDetailProjectById(Integer id);

	/**
	 * 支持者支持成功后，增加项目已筹集金额和支持人数
	 * @param orderProjectPO 支持的项目订单详情
	 */
	void supportProject(OrderProjectPO orderProjectPO);
}

package com.crux.crowd.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.crowd.member.entity.po.OrderProjectPO;
import com.crux.crowd.member.entity.po.ProjectPO;
import com.crux.crowd.member.entity.vo.*;

import java.util.List;

public interface ProjectService extends IService<ProjectPO>{


	void saveProject(ProjectVO projectVO);

	List<PortalTypeVO> listPortalProject();

	DetailProjectVO getDetailProjectById(Integer id);

	/**
	 * 支持者支持成功后，增加项目已筹集金额和支持人数
	 * @param orderProjectPO 支持的项目订单详情
	 */
	void supportProject(OrderProjectPO orderProjectPO);

	/**
	 * 会员中心-我的众筹-我发布的
	 * @param memberId 会员id
	 * @return 此会员所有发布的项目
	 */
	List<MemberProjectVO> listMemberProject(Integer memberId);

	List<MemberSupportProjectVO> listMemberSupportProject(Integer memberId);

	boolean removeByIdAndMemberId(Integer id, Integer memberId);
}

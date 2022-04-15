package com.crux.crowd.member.entity.vo;


import com.crux.crowd.member.entity.po.TypePO;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页项目分类数据
 * @since 2022-04-12
 */
@NoArgsConstructor
public class PortalTypeVO extends TypePO{
	private static final long serialVersionUID = 1L;

	/**
	 * 该分类下的项目(仅4条)
	 */
	private List<PortalProjectVO> portalProjectVOList = new ArrayList<>();

	public PortalTypeVO(Integer id, String name, String remark, List<? extends PortalProjectVO> portalProjectVOList){
		super(id, name, remark);
		this.portalProjectVOList.addAll(portalProjectVOList);
	}

	public List<PortalProjectVO> getPortalProjectVOList(){
		return portalProjectVOList;
	}

	public void setPortalProjectVOList(List<? extends PortalProjectVO> portalProjectVOList){
		this.portalProjectVOList = new ArrayList<>(portalProjectVOList);
	}

	@Override
	public String toString(){
		return super.toString() +
				"; {portalProjectVOList=" + portalProjectVOList +
				'}';
	}
}

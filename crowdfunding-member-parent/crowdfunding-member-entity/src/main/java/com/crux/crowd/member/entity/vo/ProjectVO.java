package com.crux.crowd.member.entity.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 项目表单信息
 */
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 分类ID集合
	 */
	private List<Integer> typeIdList = new ArrayList<>();
	/**
	 * 标签 id 集合
	 */
	private List<Integer> tagIdList = new ArrayList<>();
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 项目描述
 	 */
	private String projectDescription;
	/**
	 * 计划筹集的金额
	 */
	private BigDecimal money;
	/**
	 * 	筹集资金的天数
	 */
	private Integer day;
	/**
	 * 头图的路径
 	 */
	private String headerPicturePath;
	/**
	 *	详情图片的路径
	 */
	private List<String> detailPicturePathList = new ArrayList<>();
	/**
	 * 	发起人信息
 	 */
	private MemberLaunchInfoVO memberLaunchInfoVO;
	/**
	 * 	回报信息集合
 	 */
	private List<ReturnVO> returnVOList = new ArrayList<>();
	/**
	 * 	发起人确认信息
 	 */
	private MemberConfirmInfoVO memberConfirmInfoVO;

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		ProjectVO other = (ProjectVO)obj;
		return 	Objects.equals(projectName, other.projectName) &&
				Objects.equals(memberLaunchInfoVO, other.memberLaunchInfoVO) &&
				Objects.equals(memberConfirmInfoVO, other.memberConfirmInfoVO);
	}

	@Override
	public int hashCode(){
		return Objects.hash(projectName, memberLaunchInfoVO, memberConfirmInfoVO);
	}
}

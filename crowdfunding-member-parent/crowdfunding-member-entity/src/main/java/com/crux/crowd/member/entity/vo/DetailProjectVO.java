package com.crux.crowd.member.entity.vo;

import com.crux.crowd.member.entity.po.ProjectPO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 详细的项目视图信息
 * @since 2022-04-12
 */
@Getter
@Setter
@NoArgsConstructor
public class DetailProjectVO extends PortalProjectVO{

	private String projectDescription;
	private Integer follower;
	private BigDecimal supportMoney;
	private MemberLaunchInfoVO memberLaunchInfoVO;
	private List<String> detailPicturePath = new ArrayList<>();
	private List<DetailReturnVO> detailReturnVO = new ArrayList<>();

	public DetailProjectVO(Integer id, String projectName, String headerPicturePath, BigDecimal money, LocalDate deadline, Integer completion, Integer supporter, ProjectPO.Status status,
						   String projectDescription, Integer follower, BigDecimal supportMoney, MemberLaunchInfoVO memberLaunchInfoVO, List<String> detailPicturePath, List<DetailReturnVO> detailReturnVO){
		super(id, projectName, headerPicturePath, money, deadline, completion, supporter, status);
		this.projectDescription = projectDescription;
		this.follower = follower;
		this.supportMoney = supportMoney;
		this.memberLaunchInfoVO = memberLaunchInfoVO;
		this.detailPicturePath.addAll(detailPicturePath);
		this.detailReturnVO.addAll(detailReturnVO);
	}

	@Override
	public String toString(){
		return super.toString() +
				"; {projectDescription=" + projectDescription +
				",follower=" + follower +
				",supportMoney=" + supportMoney +
				",memberLaunchInfoVO=" + memberLaunchInfoVO +
				",detailPicturePath=" + detailPicturePath +
				",detailReturnVO=" + detailReturnVO +
				'}';

	}

}

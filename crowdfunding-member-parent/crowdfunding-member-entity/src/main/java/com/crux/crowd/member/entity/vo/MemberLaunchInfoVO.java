package com.crux.crowd.member.entity.vo;

import lombok.*;

import java.util.Objects;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLaunchInfoVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 	简单介绍
 	 */
	private String descriptionSimple;
	/**
	 * 	详细介绍
 	 */
	private String descriptionDetail;
	/**
	 * 	联系电话
 	 */
	private String phoneNum;
	/**
	 * 	客服电话
 	 */
	private String serviceNum;

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		MemberLaunchInfoVO other = (MemberLaunchInfoVO)obj;
		return Objects.equals(phoneNum, other.phoneNum) && Objects.equals(serviceNum, other.serviceNum);
	}

	@Override
	public int hashCode(){
		return Objects.hash(phoneNum, serviceNum);
	}
}

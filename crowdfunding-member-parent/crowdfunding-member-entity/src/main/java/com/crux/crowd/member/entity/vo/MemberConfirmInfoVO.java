package com.crux.crowd.member.entity.vo;

import lombok.*;

import java.util.Objects;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberConfirmInfoVO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	// 支付宝账号
	private String payNum;
	// 身份证号
	private String cardNum;

	private Integer memberId;

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		MemberConfirmInfoVO other = (MemberConfirmInfoVO)obj;
		return Objects.equals(memberId, other.memberId);
	}

	@Override
	public int hashCode(){
		return Objects.hash(payNum);
	}
}

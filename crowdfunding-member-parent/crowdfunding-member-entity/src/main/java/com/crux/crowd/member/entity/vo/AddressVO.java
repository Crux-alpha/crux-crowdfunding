package com.crux.crowd.member.entity.vo;

import com.crux.crowd.member.entity.po.AddressPO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressVO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 收件人
	 */
	private String receiveName;
	/**
	 * 收件人手机号
	 */
	private String phoneNum;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 所属用户id
	 */
	private Integer memberId;

	public AddressVO(AddressPO addressPO){
		receiveName = addressPO.getReceiveName();
		phoneNum = addressPO.getPhoneNum();
		address = addressPO.getAddress();
		memberId = addressPO.getMemberId();
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		AddressVO other = (AddressVO)obj;
		return Objects.equals(receiveName, other.receiveName) &&
				Objects.equals(phoneNum, other.phoneNum) &&
				Objects.equals(address, other.address) &&
				Objects.equals(memberId, other.memberId);
	}

	@Override
	public int hashCode(){
		return Objects.hash(receiveName, phoneNum, address, memberId);
	}

	@Override
	public String toString(){
		return getClass().getSimpleName() +
				"{receiveName=" + receiveName +
				", phoneNum=" + phoneNum +
				", address=" + address +
				", memberId=" + memberId +
				'}';
	}
}

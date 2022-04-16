package com.crux.crowd.member.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_address")
public class AddressPO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
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

	@Override
	public final boolean equals(Object obj){
		if(this == obj) return true;
		if(!(obj instanceof AddressPO)) return false;
		AddressPO addressPO = (AddressPO)obj;
		return Objects.equals(id, addressPO.id);
	}

	@Override
	public final int hashCode(){
		return Objects.hash(id);
	}

	@Override
	public String toString(){
		return getClass().getSimpleName() +
				"{id=" + id +
				", receiveName=" + receiveName +
				", phoneNum=" + phoneNum +
				", address=" + address +
				", memberId=" + memberId +
				'}';
	}
}

package com.crux.crowd.member.entity.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Member存入Session中的数据信息
 */
@Data
@Getter
@Setter
public class MemberInfoVO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String loginAcct;
	private String userName;
	private String email;
	private String phone;

	public String getUserName(){
		return Objects.isNull(userName) || userName.isEmpty() ? loginAcct : userName;
	}
}

package com.crux.crowd.member.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Member视图实体
 * @since 2022/04/02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private String loginAcct;
	private String userName;
	private String userPswd;
	private String email;
	private String phone;
}

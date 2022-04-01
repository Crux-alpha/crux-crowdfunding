package com.crux.crowd.member.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_member")
public class MemberPO{
	@TableId(type = IdType.AUTO)
	private Integer id;
	private String loginAcct;
	private String userName;
	private String userPswd;
	private Integer authStatus;
	private Integer userType;
	private String realName;
	private String cardNum;
	private Integer acctType;

	public MemberPO(String loginAcct, String userPswd){
		this.loginAcct = loginAcct;
		this.userPswd = userPswd;
	}
}

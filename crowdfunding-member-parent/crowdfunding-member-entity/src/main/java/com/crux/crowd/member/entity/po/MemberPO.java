package com.crux.crowd.member.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Member数据实体
 * @since 2022/03/30
 */
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
	private String phone;
	private String email;
	private Integer authStatus;
	private Integer userType;
	private String realName;
	private String cardNum;
	private Integer acctType;

	public MemberPO(String loginAcct, String userPswd, String phone){
		this.loginAcct = loginAcct;
		this.userPswd = userPswd;
		this.phone = phone;
	}
}

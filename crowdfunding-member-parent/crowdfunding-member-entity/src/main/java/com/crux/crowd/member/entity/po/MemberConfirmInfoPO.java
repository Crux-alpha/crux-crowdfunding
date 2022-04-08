package com.crux.crowd.member.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 发起人确认信息
 * @since 2022/04/07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_member_confirm_info")
public class MemberConfirmInfoPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 会员id
     */
    private Integer memberId;

    /**
     * 易付宝企业账号
     */
    private String payNum;

    /**
     * 法人身份证号
     */
    private String cardNum;

    public MemberConfirmInfoPO(Integer memberId, String payNum, String cardNum){
        this(null, memberId, payNum, cardNum);
    }
}

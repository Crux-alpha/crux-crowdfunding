package com.crux.crowd.member.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 作者
 * @since 2022-04-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_member_launch_info")
public class MemberLaunchInfoPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 会员id
     */
    private Integer memberId;

    /**
     * 简单介绍
     */
    private String descriptionSimple;

    /**
     * 详细介绍
     */
    private String descriptionDetail;

    /**
     * 联系电话
     */
    private String phoneNum;

    /**
     * 客服电话
     */
    private String serviceNum;

    public MemberLaunchInfoPO(Integer memberId, String descriptionSimple, String descriptionDetail, String phoneNum, String serviceNum){
        this(null, memberId, descriptionSimple, descriptionDetail, phoneNum, serviceNum);
    }
}

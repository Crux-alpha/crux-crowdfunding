package com.crux.crowd.member.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 项目信息
 * @since 2022-04-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_project")
public class ProjectPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目描述
     */
    private String projectDescription;

    /**
     * 筹集金额
     */
    private BigDecimal money;

    /**
     * 筹集天数
     */
    private Integer day;

    /**
     * 0-即将开始;1-众筹中;2-众筹成功;3-众筹失败
     */
    private Integer status;

    /**
     * 项目发起时间
     */
    private LocalDateTime deployDate;

    /**
     * 已筹集金额
     */
    private BigDecimal supportMoney;

    /**
     * 支持人数
     */
    private Integer supporter;

    /**
     * 百分比完成度
     */
    private Integer completion;

    /**
     * 发起人会员ID
     */
    private Integer memberId;

    /**
     * 项目创建时间
     */
    private LocalDateTime createDate;

    /**
     * 关注人数
     */
    private Integer follower;

    /**
     * 头图路径
     */
    private String headerPicturePath;

}

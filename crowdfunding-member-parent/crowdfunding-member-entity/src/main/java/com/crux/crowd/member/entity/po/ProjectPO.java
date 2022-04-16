package com.crux.crowd.member.entity.po;

import com.baomidou.mybatisplus.annotation.*;
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
    @TableField(fill = FieldFill.INSERT)
    private Status status;

    /**
     * 项目发起时间(审核通过后的日期)
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime deployDate;

    /**
     * 已筹集金额
     */
    @TableField(fill = FieldFill.INSERT)
    private BigDecimal supportMoney;

    /**
     * 支持人数
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer supporter;

    /**
     * 百分比完成度
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer completion;

    /**
     * 发起人ID
     */
    private Integer memberLaunchId;

    /**
     * 项目创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 关注人数
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer follower;

    /**
     * 头图路径
     */
    private String headerPicturePath;

    public ProjectPO(String projectName, String projectDescription, BigDecimal money, Integer day, Integer memberLaunchId, String headerPicturePath){
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.money = money;
        this.day = day;
        this.memberLaunchId = memberLaunchId;
        this.headerPicturePath = headerPicturePath;
    }

    public enum Status{
        START("即将开始"), CROWDFUNDING("众筹中"), SUCCESS("众筹成功"), FAILED("众筹失败");

        private final String status;

        Status(String status){
            this.status = status;
        }

        public String getStatus(){
            return status;
        }
    }

}

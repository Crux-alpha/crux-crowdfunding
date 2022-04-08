package com.crux.crowd.member.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 回报信息
 * @since 2022-04-07
 */
@Getter
@Setter
@TableName("t_return")
public class ReturnPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer projectId;

    /**
     * 0-实物回报,1-虚拟物品回报
     */
    private Integer type;

    /**
     * 支持金额
     */
    private Integer supportMoney;

    /**
     * 回报产品限额,0为不汇报数量
     */
    private String content;

    /**
     * 设置单笔限购
     */
    private Integer signalPurchase;

    /**
     * 具体限购数量
     */
    private Integer purchase;

    /**
     * 运费,0表示包邮
     */
    private BigDecimal freight;

    /**
     * 0-不开发票,1-开发票
     */
    private Integer invoice;

    /**
     * 项目结束后多少天向支持者发送回报
     */
    private Integer returnDate;

    /**
     * 说明图片路径
     */
    private String describePicPath;


}

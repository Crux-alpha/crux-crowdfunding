package com.crux.crowd.member.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.crux.crowd.member.entity.po.ProjectPO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 个人中心-我的众筹-我支持的项目
 * @since 2022-04-21
 */
@Setter
@Getter
@ToString
public class MemberSupportProjectVO{

    /*
     订单相关
     */
    private Integer orderId;
    private String orderNum;
    private BigDecimal supportPrice;
    private Integer returnCount;
    private BigDecimal freight;

    /*
     项目相关
     */
    private String projectName;
    private ProjectPO.Status status;
    private LocalDate deadline;
    private Integer completion;
    private LocalDateTime supportDateTime;

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        MemberSupportProjectVO other = (MemberSupportProjectVO)obj;
        return Objects.equals(orderId, other.orderId);
    }

    public void setOrderNum(String orderNum){
        this.orderNum = orderNum;
        String timeString = orderNum.substring(0, 14);
        this.supportDateTime = LocalDateTime.parse(timeString, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    @Override
    public int hashCode(){
        return Objects.hash(orderId);
    }
}

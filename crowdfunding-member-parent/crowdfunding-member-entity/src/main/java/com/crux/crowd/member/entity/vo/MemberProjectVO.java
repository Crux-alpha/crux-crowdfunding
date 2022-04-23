package com.crux.crowd.member.entity.vo;

import com.crux.crowd.member.entity.po.ProjectPO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 会员中心-我发布的
 */
@Setter
@Getter
@NoArgsConstructor
public class MemberProjectVO extends PortalProjectVO{
    private static final long serialVersionUID = 1L;

    private BigDecimal supportMoney;

    public MemberProjectVO(Integer id, String projectName, String headerPicturePath, BigDecimal money, LocalDate deadline, Integer completion, Integer supporter, ProjectPO.Status status,
                           BigDecimal supportMoney){
        super(id, projectName, headerPicturePath, money, deadline, completion, supporter, status);
        this.supportMoney = supportMoney;
    }

    @Override
    public String toString(){
        return super.toString() +
                ";{supportMoney=" + supportMoney +
                '}';
    }
}

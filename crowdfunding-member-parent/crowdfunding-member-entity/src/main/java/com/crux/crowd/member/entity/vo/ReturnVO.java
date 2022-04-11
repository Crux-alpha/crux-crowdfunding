package com.crux.crowd.member.entity.vo;

import com.crux.crowd.member.entity.po.ReturnPO;
import lombok.*;

import java.util.Objects;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReturnVO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	// 回报类型
	private ReturnPO.ReturnType type;
	// 支持金额
	private BigDecimal supportMoney;
	// 回报内容介绍
	private String content;
	// 总回报数量，0 为不限制
	private Integer count;
	// 是否限制单笔购买数量
	private Boolean signalPurchase;
	// 如果单笔限购，那么具体的限购数量
	private Integer purchase = 0;
	// 运费，“0”为包邮
	private BigDecimal freight;
	// 是否开发票
	private Boolean invoice;
	// 众筹结束后返还回报物品天数
	private Integer returnDate;
	// 说明图片路径
	private String describePicPath;

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		ReturnVO returnVO = (ReturnVO)obj;
		return type == returnVO.type &&
				Objects.equals(supportMoney, returnVO.supportMoney) &&
				Objects.equals(content, returnVO.content) &&
				Objects.equals(count, returnVO.count) &&
				Objects.equals(signalPurchase, returnVO.signalPurchase) &&
				Objects.equals(purchase, returnVO.purchase) &&
				Objects.equals(freight, returnVO.freight) &&
				Objects.equals(invoice, returnVO.invoice);
	}

	@Override
	public int hashCode(){
		return Objects.hash(type, supportMoney, content, count, signalPurchase, purchase, freight, invoice);
	}
}

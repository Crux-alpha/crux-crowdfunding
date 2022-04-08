package com.crux.crowd.member.entity.vo;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReturnVO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	// 回报类型
	private ReturnType type;
	// 支持金额
	private BigDecimal supportMoney;
	// 回报内容介绍
	private String content;
	// 总回报数量，0 为不限制
	private Integer count;
	// 是否限制单笔购买数量
	private boolean signalPurchase;
	// 如果单笔限购，那么具体的限购数量
	private Integer purchase = 0;
	// 运费，“0”为包邮
	private BigDecimal freight;
	// 是否开发票
	private boolean invoice;
	// 众筹结束后返还回报物品天数
	private Integer returnDate;
	// 说明图片路径
	private String describePicPath;

	/**
	 * 回报类型
	 */
	public enum ReturnType{
		PRACTICAL("实物回报"), FICTITIOUS("虚拟回报");

		private final String type;

		ReturnType(String type){
			this.type = type;
		}

		public String getType(){
			return type;
		}
	}
}

package com.crux.crowd.member.entity.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class OrderVO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 收货地址id
	 */
	private Integer addressId;
	/**
	 * 订单号
	 */
	private String orderNum;
	/**
	 * 支付宝流水号
	 */
	private String payOrderNum;
	/**
	 * 订单金额
	 */
	private BigDecimal orderAmount = BigDecimal.ZERO;
	/**
	 * 是否开发票
	 */
	private Boolean invoice;
	/**
	 * 发票抬头
	 */
	private String invoiceTitle;
	/**
	 * 订单备注
	 */
	private String orderRemark;
	/**
	 * 当前订单的支持项目
	 */
	private OrderProjectVO orderProjectVO;

	public OrderVO(Integer addressId, Boolean invoice, String invoiceTitle, String orderRemark){
		this.addressId = addressId;
		this.invoice = invoice;
		this.invoiceTitle = invoiceTitle;
		this.orderRemark = orderRemark;
	}

	@Override
	public String toString(){
		return getClass().getSimpleName() +
				"{addressId=" + addressId +
				", orderNum=" + orderNum +
				", payOrderNum=" + payOrderNum +
				", orderAmount=" + orderAmount +
				", invoice=" + invoice +
				", invoiceTitle=" + invoiceTitle +
				", orderRemark=" + orderRemark +
				'}';
	}
}

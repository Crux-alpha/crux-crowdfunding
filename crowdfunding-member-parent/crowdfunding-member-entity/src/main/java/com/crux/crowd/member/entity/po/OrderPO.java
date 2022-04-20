package com.crux.crowd.member.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.crux.crowd.member.entity.vo.OrderVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 订单实体类
 * @since 2022-04-16
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_order")
public class OrderPO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
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
	private BigDecimal orderAmount;
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
	 * 收货地址id
	 */
	private Integer addressId;

	public OrderPO(OrderVO orderVO){
		orderNum = orderVO.getOrderNum();
		payOrderNum = orderVO.getPayOrderNum();
		orderAmount = orderVO.getOrderAmount();
		invoice = orderVO.getInvoice();
		invoiceTitle = orderVO.getInvoiceTitle();
		orderRemark = orderVO.getOrderRemark();
		addressId = orderVO.getAddressId();
	}

	@Override
	public final boolean equals(Object obj){
		if(this == obj) return true;
		if(!(obj instanceof OrderPO)) return false;
		OrderPO orderPO = (OrderPO)obj;
		return Objects.equals(id, orderPO.id);
	}

	@Override
	public final int hashCode(){
		return Objects.hash(id);
	}

	@Override
	public String toString(){
		return getClass().getSimpleName() +
				"{id=" + id +
				",orderNum=" + orderNum +
				",payOrderNum=" + payOrderNum +
				",orderAmount=" + orderAmount +
				",invoice=" + invoice +
				",invoiceTitle=" + invoiceTitle +
				",orderRemark=" + orderRemark +
				",addressId=" + addressId +
				'}';
	}
}

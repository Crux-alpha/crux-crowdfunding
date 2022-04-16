package com.crux.crowd.member.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProjectVO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 项目名
	 */
	private String projectName;
	/**
	 * 发起人
	 */
	private String launchName;
	/**
	 * 回报内容
	 */
	private String returnContent;
	/**
	 * 回报数量
	 */
	private Integer returnCount;
	/**
	 * 支持单价
	 */
	private BigDecimal supportPrice;
	/**
	 * 运费
	 */
	private BigDecimal freight;
	/**
	 * 所属订单id
	 */
	private Integer orderId;
	/**
	 * 限购数量
	 */
	private Integer purchase;
	/**
	 * 是否限购
	 */
	private Boolean signalPurchase;

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		OrderProjectVO other = (OrderProjectVO)obj;
		return Objects.equals(orderId, other.orderId);
	}

	@Override
	public int hashCode(){
		return Objects.hash(orderId);
	}

	@Override
	public String toString(){
		return getClass().getSimpleName() +
				"{projectName=" + projectName +
				", launchName=" + launchName +
				", returnContent=" + returnContent +
				", returnCount=" + returnCount +
				", supportPrice=" + supportPrice +
				", freight=" + freight +
				", orderId=" + orderId +
				",signalPurchase=" + signalPurchase +
				",purchase=" + purchase +
				'}';
	}
}

package com.crux.crowd.member.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.crux.crowd.member.entity.vo.OrderProjectVO;
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
@TableName("t_order_project")
public class OrderProjectPO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
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
	 * 所属回报id
	 */
	private Integer returnId;

	public OrderProjectPO(OrderProjectVO vo){
		projectName = vo.getProjectName();
		launchName = vo.getLaunchName();
		returnContent = vo.getReturnContent();
		returnCount = vo.getReturnCount();
		supportPrice = vo.getSupportPrice();
		freight = vo.getFreight();
		orderId = vo.getOrderId();
		returnId = vo.getReturnId();
	}

	@Override
	public final boolean equals(Object obj){
		if(this == obj) return true;
		if(!(obj instanceof OrderProjectPO)) return false;
		OrderProjectPO other = (OrderProjectPO)obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public final int hashCode(){
		return Objects.hash(id);
	}

	@Override
	public String toString(){
		return getClass().getSimpleName() +
				"{id=" + id +
				", projectName=" + projectName +
				", launchName=" + launchName +
				", returnContent=" + returnContent +
				", returnCount=" + returnCount +
				", supportPrice=" + supportPrice +
				", freight=" + freight +
				", orderId=" + orderId +
				'}';
	}
}

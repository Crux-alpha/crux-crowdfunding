package com.crux.crowd.member.entity.vo;

import com.crux.crowd.member.entity.po.ReturnPO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 详细的项目回报视图信息
 * @since 2022-04-12
 */
@Getter
@Setter
@NoArgsConstructor
public class DetailReturnVO extends ReturnVO{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer projectId;

	public DetailReturnVO(ReturnPO.ReturnType type, BigDecimal supportMoney, String content, Integer count, Boolean signalPurchase, Integer purchase, BigDecimal freight, Boolean invoice, Integer returnDate, String describePicPath,
						  Integer id, Integer projectId){
		super(type, supportMoney, content, count, signalPurchase, purchase, freight, invoice, returnDate, describePicPath);
		this.id = id;
		this.projectId = projectId;
	}

	@Override
	public final boolean equals(Object obj){
		if(this == obj) return true;
		if(!(obj instanceof DetailReturnVO)) return false;
		DetailReturnVO other = (DetailReturnVO)obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public final int hashCode(){
		return Objects.hash(id);
	}

	@Override
	public String toString(){
		return super.toString() +
				";{id=" + id +
				",projectId=" + projectId +
				'}';
	}
}

package com.crux.crowd.member.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Objects;

/**
 * 首页要展示的项目数据
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PortalProjectVO implements java.io.Serializable{

	private Integer id;
	private String projectName;
	private String headerPicturePath;
	private BigDecimal money;
	private LocalDate deadline;
	private Integer completion;
	private Integer supporter;

	public long remainingDay(){
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime deadline = this.deadline.atStartOfDay();
		if(now.isAfter(deadline)) return -1;
		return Duration.between(now, deadline).toDays();
	}

	@Override
	public final boolean equals(Object obj){
		if(this == obj) return true;
		if(!(obj instanceof PortalProjectVO)) return false;
		PortalProjectVO other = (PortalProjectVO)obj;
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
				", headerPicturePath=" + headerPicturePath +
				", money=" + money +
				", deadline=" + deadline +
				", completion=" + completion +
				", supporter=" + supporter +
				'}';
	}
}

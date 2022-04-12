package com.crux.crowd.member.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.StrictFill;
import com.crux.crowd.member.entity.po.ProjectPO;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.function.Supplier;

@Component
public class FieldAutoFillHandler implements MetaObjectHandler{

	public static final Supplier<Integer> GET_ZERO = () -> 0;

	/**
	 * 插入操作自动填充
	 * @param metaObject 元对象
	 */
	@Override
	public void insertFill(MetaObject metaObject){
		Object original = metaObject.getOriginalObject();

		if(original instanceof ProjectPO){
			strictInsertFillProjectPO(metaObject);
		}
	}

	@Override
	public void updateFill(MetaObject metaObject){

	}

	/**
	 * ProjectPO插入操作自动填充
	 * @param metaObject 原对象为{@link ProjectPO}的元对象
	 */
	private void strictInsertFillProjectPO(MetaObject metaObject){
		strictInsertFill(findTableInfo(metaObject), metaObject,
				Arrays.asList(StrictFill.of("status", () -> ProjectPO.Status.START, ProjectPO.Status.class),
						StrictFill.of("supportMoney", () -> BigDecimal.ZERO, BigDecimal.class),
						StrictFill.of("supporter", GET_ZERO, Integer.class),
						StrictFill.of("completion", GET_ZERO, Integer.class),
						StrictFill.of("createDate", LocalDateTime::now, LocalDateTime.class),
						StrictFill.of("follower", GET_ZERO, Integer.class),
						StrictFill.of("deployDate", () -> LocalDateTime.now().plusDays(1), LocalDateTime.class)));
	}
}

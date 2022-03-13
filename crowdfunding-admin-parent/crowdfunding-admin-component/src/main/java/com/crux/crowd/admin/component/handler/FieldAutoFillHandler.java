package com.crux.crowd.admin.component.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.crux.crowd.common.util.CrowdConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 公共字段自动填充
 */
@Slf4j
@Component
public class FieldAutoFillHandler implements MetaObjectHandler{

	@Override
	public void insertFill(MetaObject metaObject){
		Object entity = metaObject.getOriginalObject();
		log.debug("为{}填充createTime字段...", entity.getClass().getSimpleName());
		strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now().format(CrowdConstant.DEFAULT_TIME_FORMAT), String.class);
		log.debug("填充完成！");
	}

	@Override
	public void updateFill(MetaObject metaObject){
	}
}

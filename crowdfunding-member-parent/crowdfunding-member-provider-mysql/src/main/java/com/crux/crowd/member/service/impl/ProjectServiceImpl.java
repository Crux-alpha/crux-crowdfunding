package com.crux.crowd.member.service.impl;

import com.crux.crowd.member.entity.po.ProjectPO;
import com.crux.crowd.member.mapper.ProjectPOMapper;
import com.crux.crowd.member.service.AbstractService;
import com.crux.crowd.member.service.ProjectService;
import com.crux.crowd.member.service.ServiceException;

import java.util.function.Supplier;

public class ProjectServiceImpl extends AbstractService<ProjectPOMapper,ProjectPO> implements ProjectService{


	@Override
	protected <R> R execute(Supplier<R> method) throws ServiceException{
		return null;
	}
}

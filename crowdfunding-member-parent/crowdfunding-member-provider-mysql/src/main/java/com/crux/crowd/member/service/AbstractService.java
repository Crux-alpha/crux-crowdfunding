package com.crux.crowd.member.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Collection;
import java.util.function.Supplier;

public abstract class AbstractService<M extends BaseMapper<T>,T> extends ServiceImpl<M,T>{

	public QueryWrapper<T> queryWrapper(){
		return (QueryWrapper<T>)query().getWrapper();
	}

	public LambdaQueryWrapper<T> lambdaQueryWrapper(){
		return (LambdaQueryWrapper<T>)lambdaQuery().getWrapper();
	}

	public UpdateWrapper<T> updateWrapper(){
		return (UpdateWrapper<T>)update().getWrapper();
	}

	public LambdaUpdateWrapper<T> lambdaUpdateWrapper(){
		return (LambdaUpdateWrapper<T>)lambdaUpdate().getWrapper();
	}

	@Override
	public boolean save(final T entity) throws ServiceException{
		return execute(() -> super.save(entity));
	}

	@Override
	public boolean saveBatch(final Collection<T> entityList, final int batchSize){
		return execute(() -> super.saveBatch(entityList, batchSize));
	}

	@Override
	public boolean updateById(final T entity) throws ServiceException{
		return execute(() -> super.updateById(entity));
	}

	@Override
	public boolean update(final T entity, final Wrapper<T> updateWrapper) throws ServiceException{
		return execute(() -> super.update(entity, updateWrapper));
	}

	/**
	 * 通过此方法对save/update方法抛出的异常进行包装
	 * @param method save/update方法
	 * @param <R> 返回值类型
	 * @return method的返回值
	 * @throws ServiceException 将method抛出的异常包装为业务异常
	 */
	protected abstract <R> R execute(Supplier<R> method) throws ServiceException;
}

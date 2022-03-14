package com.crux.crowd.admin.component.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.dao.DuplicateKeyException;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 包装ServiceImpl的部分方法。主要是处理异常
 * @since 2022/03/14
 */
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

	/**
	 * 同{@link #execute(Supplier)}，不过可以将{@link DuplicateKeyException}包装为自定义异常
	 * @param method save/update方法
	 * @param exFunction 异常转换器。可以将{@link DuplicateKeyException}转换成自定义异常
	 * @param <R> 返回值类型
	 * @param <X> 包装异常类型
	 * @return method的返回值
	 * @throws X 包装后的异常
	 */
	protected <R,X extends ServiceException> R execute(Supplier<R> method, Function<DuplicateKeyException,? extends X> exFunction) throws X{
		try{
			return method.get();
		}catch(DuplicateKeyException e){
			throw exFunction.apply(e);
		}
	}
}

package com.crux.crowd.member.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.crux.crowd.member.entity.po.*;
import com.crux.crowd.member.entity.vo.*;
import com.crux.crowd.member.mapper.*;
import com.crux.crowd.member.service.AbstractService;
import com.crux.crowd.member.service.ProjectService;
import com.crux.crowd.member.service.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service("projectService")
public class ProjectServiceImpl extends AbstractService<ProjectPOMapper,ProjectPO> implements ProjectService{

	protected final ProjectItemPicPOMapper pipMapper;
	protected final MemberLaunchInfoPOMapper mliMapper;
	protected final MemberConfirmInfoPOMapper mciMapper;
	protected final ReturnPOMapper returnPOMapper;

	public ProjectServiceImpl(ProjectItemPicPOMapper pipMapper, MemberLaunchInfoPOMapper mliMapper, MemberConfirmInfoPOMapper mciMapper, ReturnPOMapper returnPOMapper){
		this.pipMapper = pipMapper;
		this.mliMapper = mliMapper;
		this.mciMapper = mciMapper;
		this.returnPOMapper = returnPOMapper;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void saveProject(ProjectVO projectVO, Integer memberId){
		/* 保存发起人信息 */
		MemberLaunchInfoVO mliVO = projectVO.getMemberLaunchInfoVO();
		MemberLaunchInfoPO mliPO = new MemberLaunchInfoPO(memberId,
				mliVO.getDescriptionSimple(),
				mliVO.getDescriptionDetail(),
				mliVO.getPhoneNum(),
				mliVO.getServiceNum());
		saveMemberLaunchInfo(mliPO);

		/* 保存ProjectPO */
		// 1、将ProjectVO转为ProjectPO，保存
		ProjectPO projectPO = new ProjectPO(
				projectVO.getProjectName(),
				projectVO.getProjectDescription(),
				projectVO.getMoney(),
				projectVO.getDay(),
				mliPO.getId(),
				projectVO.getHeaderPicturePath());
		save(projectPO);
		// 2、获取自增主键的ID
		Integer projectId = projectPO.getId();

		/* 保存项目-分类关联信息 */
		saveType(projectId, projectVO.getTypeIdList());

		/* 保存项目-标签关联信息 */
		saveTag(projectId, projectVO.getTagIdList());

		/* 保存项目详情图片关联信息 */
		List<ProjectItemPicPO> pipPOList = projectVO.getDetailPicturePathList().stream()
				.map(path -> new ProjectItemPicPO(projectId, path)).collect(Collectors.toList());
		saveItemPicture(pipPOList);

		/* 保存回报信息 */
		List<ReturnPO> returnPOList = projectVO.getReturnVOList().stream().map(v -> new ReturnPO(null, projectId,
				v.getType(),
				v.getSupportMoney(),
				v.getCount(),
				v.getContent(),
				v.getSignalPurchase(),
				v.getPurchase(),
				v.getFreight(),
				v.getInvoice(),
				v.getReturnDate(),
				v.getDescribePicPath())).collect(Collectors.toList());
		saveReturn(returnPOList);

		/* 保存项目确认信息 */
		MemberConfirmInfoVO mciVO = projectVO.getMemberConfirmInfoVO();
		MemberConfirmInfoPO mciPO = new MemberConfirmInfoPO(memberId, mciVO.getPayNum(), mciVO.getCardNum());
		saveMemberConfirmInfo(mciPO);
	}

	@Override
	public List<PortalTypeVO> listPortalProject(){
		return baseMapper.selectPortalProjectAll();
	}

	@Override
	public DetailProjectVO getDetailProjectById(Integer id){
		return baseMapper.selectDetailProject(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void supportProject(OrderProjectPO orderProjectPO){
		ReturnPO returnPO;
		int returnCount;
		synchronized(returnPOMapper){
			returnPO = returnPOMapper.selectById(orderProjectPO.getReturnId());
			// 修改回报数量
			returnCount = orderProjectPO.getReturnCount();
			int count = returnPO.getCount();
			if(count > 0){
				int newCount = count - returnCount;
				if(newCount < 0){    // 如果数量小于0，停止
					throw new ServiceException(returnPO.getContent() + "的回报数量小于0！");
				}
				// 如果count等于0，将其减1表示已经售罄
				if(newCount == 0) newCount--;

				// 更新字段：回报数量
				returnPOMapper.update(null, Wrappers.<ReturnPO>lambdaUpdate()
						.set(ReturnPO::getCount, newCount)
						.eq(ReturnPO::getId, returnPO.getId()));
			}
		}

		synchronized(this){
			// 修改项目支持金额、支持人数、状态
			ProjectPO projectPO = getById(returnPO.getProjectId());
			// 如果已经众筹成功，不再增加
			if(projectPO.getStatus() == ProjectPO.Status.SUCCESS || projectPO.getStatus() == ProjectPO.Status.FAILED){
				throw new ServiceException(projectPO.getProjectName() + "已经众筹结束！");
			}
			// 计算支持金额
			BigDecimal addMoney = orderProjectPO.getSupportPrice().multiply(BigDecimal.valueOf(returnCount));
			BigDecimal supportMoney = projectPO.getSupportMoney().add(addMoney);
			projectPO.setSupportMoney(supportMoney);

			// 更新字段：已筹集金额、百分比完成度、众筹状态、支持人数
			update(lambdaUpdateWrapper()
					.set(ProjectPO::getSupportMoney, projectPO.getSupportMoney())
					.set(ProjectPO::getCompletion, projectPO.getCompletion())
					.set(ProjectPO::getStatus, projectPO.getStatus())
					.set(ProjectPO::getSupporter, projectPO.getSupporter() + 1)
					.eq(ProjectPO::getId, projectPO.getId()));
		}
	}

	/**
	 * 保存项目类型
	 * @param id 项目id
	 * @param typeIdList 类型集合
	 */
	protected void saveType(final Integer id, final List<Integer> typeIdList){
		execute(() -> SqlHelper.retBool(
				baseMapper.insertType(id, Optional.ofNullable(typeIdList).filter(list -> !list.isEmpty()).orElseThrow(() -> new ServiceException("项目没有指定分类")))
			)
		);
	}

	/**
	 * 保存项目标签
	 * @param id 项目id
	 * @param tagIdList 标签集合
	 */
	protected void saveTag(final Integer id, final List<Integer> tagIdList){
		execute(() -> tagIdList.isEmpty() || SqlHelper.retBool(baseMapper.insertTag(id, tagIdList)));
	}

	/**
	 * 保存项目详情图地址
	 * @param pipPOList 项目-详情图关联对象集合
	 */
	protected void saveItemPicture(final List<ProjectItemPicPO> pipPOList){
		execute(() -> pipPOList.stream().map(pipMapper::insert).allMatch(SqlHelper::retBool));
	}

	/**
	 * 保存项目发起人信息
	 * @param mliPO 项目发起人信息
	 */
	protected void saveMemberLaunchInfo(final MemberLaunchInfoPO mliPO){
		execute(() -> SqlHelper.retBool(mliMapper.insert(mliPO)));
	}

	/**
	 * 保存项目提交信息
	 * @param mciPO 发起人提交信息
	 */
	protected void saveMemberConfirmInfo(final MemberConfirmInfoPO mciPO){
		execute(() -> SqlHelper.retBool(mciMapper.insert(mciPO)));
	}

	/**
	 * 保存项目回报信息
	 * @param returnPOList 回报信息集合
	 */
	protected void saveReturn(final List<ReturnPO> returnPOList){
		execute(() -> returnPOList.stream().map(returnPOMapper::insert).allMatch(SqlHelper::retBool));
	}

	/**
	 * 如果方法的返回值为false，则抛出异常，并回滚事务
	 * @param method 正在执行的方法
	 * @param <R> 返回值类型
	 * @return 方法的返回值
	 * @throws ServiceException 表示当前操作未能成功保存
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	protected <R> R execute(Supplier<R> method) throws ServiceException{
		R r = method.get();
		if(r instanceof Boolean && !((Boolean)r)) throw new ServiceException("未能成功保存");
		return r;
	}
}

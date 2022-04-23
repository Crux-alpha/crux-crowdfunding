package com.crux.crowd.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crux.crowd.member.entity.po.ProjectPO;
import com.crux.crowd.member.entity.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Project表
 * @since 2022-04-07
 */
@Mapper
public interface ProjectPOMapper extends BaseMapper<ProjectPO>{

	/**
	 * 保存项目的分类信息
	 * @param id 项目id
	 * @param typeIdList 项目的所有分类id
	 * @return 影响条数
	 */
	int insertType(@Param("id") Integer id, @Param("typeIdList") List<Integer> typeIdList);

	/**
	 * 保存项目标签
	 * @param id 项目id
	 * @param tagIdList 项目的所有标签id，不能为空
	 * @return 影响条数
	 */
	int insertTag(@Param("id") Integer id, @Param("tagIdList") List<Integer> tagIdList);

	/**
	 * 查询用以首页显示的数据
	 * @return 首页项目展示信息，根据Type分类
	 */
	List<PortalTypeVO> selectPortalProjectAll();

	/**
	 * 查询项目详细视图数据
	 * @param id 项目id
	 * @return 详细的项目视图数据
	 */
	DetailProjectVO selectDetailProject(Integer id);

	List<MemberProjectVO> selectMemberProjectByMemberId(Integer memberId);

	List<MemberSupportProjectVO> selectMemberSupportProjectByMemberId(Integer memberId);

	int removeProjectTag(Integer id);

	int removeProjectType(Integer id);
}

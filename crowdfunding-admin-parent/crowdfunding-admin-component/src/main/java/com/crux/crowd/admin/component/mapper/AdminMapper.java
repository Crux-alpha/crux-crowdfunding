package com.crux.crowd.admin.component.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crux.crowd.admin.entity.Admin;

import java.util.List;

/**
 * t_admin的持久层
 * @since 2022-03-09
 */
public interface AdminMapper extends BaseMapper<Admin>{

	int deleteAssignRolesById(Integer id);

	int insertAssignRolesById(Integer id, List<Integer> roleIds);
}

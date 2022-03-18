package com.crux.crowd.admin.component.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crux.crowd.admin.entity.Role;

import java.util.List;

/**
 * t_role的持久层
 * @since 2022-03-14
 */
public interface RoleMapper extends BaseMapper<Role>{

	/**
	 * 根据Admin id查询分配的角色信息
	 * @param adminId admin的id
	 * @param assigned true查询分配，false查询未分配
	 * @return 查询结果集
	 */
	List<Role> selectRolesAssignedByAdminId(Integer adminId, boolean assigned);
}

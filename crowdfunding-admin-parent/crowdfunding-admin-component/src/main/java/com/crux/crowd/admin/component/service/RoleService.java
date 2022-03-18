package com.crux.crowd.admin.component.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.crowd.admin.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role>{

	Page<Role> pageFuzzy(int current, int size, String keyword);

	List<Role> getRolesAssigned(Integer adminId, boolean assigned);
}

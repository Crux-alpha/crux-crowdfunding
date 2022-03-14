package com.crux.crowd.admin.component.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.crowd.admin.entity.Role;

public interface RoleService extends IService<Role>{

	Page<Role> pageFuzzy(int current, int size, String keyword);
}

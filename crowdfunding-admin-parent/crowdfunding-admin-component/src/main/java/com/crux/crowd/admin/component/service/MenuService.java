package com.crux.crowd.admin.component.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.crowd.admin.entity.Menu;

public interface MenuService extends IService<Menu>{
	/**
	 * 查询所有节点，组装并返回根节点
	 * @return 根节点
	 */
	Menu getRootMenu();
}

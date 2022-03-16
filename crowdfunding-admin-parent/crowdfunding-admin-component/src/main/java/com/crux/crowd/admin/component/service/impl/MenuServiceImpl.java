package com.crux.crowd.admin.component.service.impl;

import com.crux.crowd.admin.component.mapper.MenuMapper;
import com.crux.crowd.admin.component.service.AbstractService;
import com.crux.crowd.admin.component.service.MenuService;
import com.crux.crowd.admin.component.service.ServiceException;
import com.crux.crowd.admin.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service("menuService")
public class MenuServiceImpl extends AbstractService<MenuMapper,Menu> implements MenuService{

	@Override
	public Menu getRootMenu(){
		// 1、获取所有menu
		List<Menu> menuList = list();
		// 2、转换成Map<menu_id,menu>
		Map<Integer,Menu> menuMap = menuList.stream().collect(Collectors.toMap(Menu::getId, Function.identity()));
		// 3、声明根节点
		Menu root = null;
		// 4、组装节点
		for(Menu menu : menuList){
			Integer pid = menu.getPid();
			if(pid == null){
				root = menu;
				continue;
			}
			Menu parent = menuMap.get(pid);
			parent.getChildren().add(menu);
		}

		return root;
	}

	@Override
	protected <R> R execute(Supplier<R> method) throws ServiceException{
		return method.get();
	}
}

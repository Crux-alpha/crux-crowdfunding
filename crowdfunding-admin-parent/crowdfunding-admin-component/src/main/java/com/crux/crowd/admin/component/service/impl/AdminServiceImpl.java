package com.crux.crowd.admin.component.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crux.crowd.admin.entity.Admin;
import com.crux.crowd.admin.component.mapper.AdminMapper;
import com.crux.crowd.admin.component.service.AdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-03-09
 */
@Service("adminService")
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements AdminService{
}

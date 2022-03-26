package com.crux.crowd.admin.component.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crux.crowd.admin.component.service.AdminService;
import com.crux.crowd.admin.entity.Admin;
import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.common.util.ResponseMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static java.util.Collections.singletonMap;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 * 管理员后台管理系统的处理器
 */
@Controller
@RequestMapping("/admin")
public class AdminController{

	private final AdminService adminService;
	static final String hasAnyRole = "hasAnyRole('经理', '超级管理员')";

	public AdminController(AdminService adminService){
		this.adminService = adminService;
	}

	/**
	 * admin登录
	 * @param account 账号
	 * @param password 密码
	 * @param session 将admin保存到session中
	 * @return 重定向到后台管理页面
	 */
	@Deprecated
	//@PostMapping(path = "/login", params = {"account", "password"})
	public String login(String account, String password, HttpSession session){
		final Admin admin = adminService.login(account, password);
		session.setAttribute(CrowdConstant.ADMIN_LOGIN_ACCOUNT, admin);
		return "redirect:/admin/main";
	}

	/**
	 * admin登出
	 * @param session 删除session中的admin
	 * @return 重定向至主页
	 */
	@Deprecated
	//@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/index";
	}

	/**
	 * 分页+模糊查询
	 * @param keyword 关键词
	 * @param current 当前页码
	 * @param size 分页大小
	 * @return 查询到的admin的分页信息
	 */
	@GetMapping(path = "/main/user", params = "current")
	@ResponseBody
	@PreAuthorize(hasAnyRole + " or hasAuthority('user:get')")
	public ResponseMessage<String,Page<Admin>> getAdminPage(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
							   @RequestParam(name = "current") int current,
							   @RequestParam(name = "size", required = false, defaultValue = "10") int size){
		Page<Admin> adminPage = adminService.pageFuzzy(current, size, keyword);
		long total = adminPage.getTotal();
		String message = total > 0 ? "查询到" + total + "条数据" : "没有查询到任何数据";
		return ResponseMessage.success(message, singletonMap("page", adminPage));
	}

	/**
	 * 通过id查询admin
	 * @param id id
	 * @return 如果能够查询到，则返回admin数据
	 */
	@GetMapping("/main/user/{id}")
	@ResponseBody
	@PreAuthorize(hasAnyRole + " or hasAuthority('user:get')")
	public ResponseMessage<String,Admin> getAdmin(@PathVariable("id") Integer id){
		Admin admin = adminService.getById(id);
		return ResponseMessage.success(singletonMap("admin", admin));
	}

	@GetMapping(path = "/main/user", params = "ids[]")
	@ResponseBody
	@PreAuthorize(hasAnyRole + " or hasAuthority('user:get')")
	public ResponseMessage<String,List<Admin>> getAdminList(@RequestParam("ids[]") List<Integer> ids){
		List<Admin> admins = adminService.listByIds(ids);
		return admins.isEmpty() ? ResponseMessage.failure("没有查询到数据", Collections.emptyMap())
								: ResponseMessage.success(singletonMap("admins", admins));
	}

	/**
	 * 添加一个admin
	 * @param admin 请求参数封装后的admin
	 * @return 如果保存成功
	 */
	@PostMapping("/main/user")
	@ResponseBody
	@PreAuthorize(hasAnyRole + " or hasAuthority('user:save')")
	public ResponseMessage<?,?> saveAdmin(Admin admin){
		adminService.save(admin);
		return ResponseMessage.success("保存成功！");
	}

	/**
	 * 根据id修改一个admin
	 * @param id id
	 * @param username 昵称
	 * @param email 邮箱
	 * @return 如果保存成功
	 */
	@PutMapping("/main/user/{id}")
	@ResponseBody
	@PreAuthorize(hasAnyRole + " or hasAuthority('user:save')")
	public ResponseMessage<?,?> updateAdmin(@PathVariable("id") Integer id, String username, String email){
		adminService.update(id, username, email);
		return ResponseMessage.success("保存成功！");
	}

	/**
	 * 根据id删除指定admin
	 * @param id id
	 * @return 如果admin存在，并且允许删除，则删除成功
	 */
	@DeleteMapping("/main/user/{id}")
	@ResponseBody
	@PreAuthorize(hasAnyRole + " or hasAuthority('user:delete')")
	public ResponseMessage<?,?> deleteAdmin(@PathVariable("id") Integer id){
		boolean result = adminService.removeById(id);
		if(result) return ResponseMessage.success("删除成功！");
		return ResponseMessage.failure("删除失败！");
	}

	@DeleteMapping("/main/user")
	@ResponseBody
	@PreAuthorize(hasAnyRole + " or hasAuthority('user:delete')")
	public ResponseMessage<?,?> deleteAdmin(@RequestParam("ids[]") List<Integer> ids){
		adminService.removeBatchByIds(ids);
		return ResponseMessage.success("删除成功！");
	}

	/**
	 * 获取当前使用的用户(主体)
	 * @return UserDetails
	 */
	@GetMapping("/main/user_details")
	@ResponseBody
	public Object getUserDetails(){
		// 通过SecurityContextHolder.getContext().getAuthentication().getPrincipal()拿到UserDetails对象
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}

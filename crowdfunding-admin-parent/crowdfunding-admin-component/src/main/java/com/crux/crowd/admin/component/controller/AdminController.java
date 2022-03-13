package com.crux.crowd.admin.component.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crux.crowd.admin.component.service.AdminService;
import com.crux.crowd.admin.entity.Admin;
import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.common.util.ResponseMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * 管理员后台管理系统的处理器<br/><br/>
 * 目前处理了：
 * <ul>
 *     <li>登录：{@link #toLogin()}，{@link #login(String, String, HttpSession)}</li>
 *     <li>主页面：{@link #main()}</li>
 *     <li>登出：{@link #logout(HttpSession)}</li>
 *     <li>管理员维护：
 *     <ul>
 *         <li>查询。包括分页、模糊：{@link #getAdminPage(String, int, int)}</li>
 *         <li>单个删除：{@link #deleteAdmin(int)}</li>
 *     </ul>
 *     </li>
 * </ul>
 */
@Controller
@RequestMapping("/admin")
public class AdminController{

	private final AdminService service;

	public AdminController(AdminService service){
		this.service = service;
	}

	@GetMapping("/login")
	public String toLogin(){
		return "admin-login";
	}

	@PostMapping("/login")
	public String login(String account, String password, HttpSession session){
		final Admin admin = service.login(account, password);
		session.setAttribute(CrowdConstant.ADMIN_LOGIN_ACCOUNT, admin);
		return "redirect:/admin/main";
	}

	@RequestMapping("/main")
	public String main(){
		return "main";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/index";
	}

	@RequestMapping("/main/user")
	public String user(){
		return "main-user";
	}

	@GetMapping(path = "/main/user", params = "current")
	@ResponseBody
	public ResponseMessage<String,Page<Admin>> getAdminPage(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
							   @RequestParam(name = "current") int current,
							   @RequestParam(name = "size", required = false, defaultValue = "10") int size){
		Page<Admin> adminPage = service.pageFuzzy(keyword, current, size);
		long total = adminPage.getTotal();
		String message = total > 0 ? "查询到" + total + "条数据" : "没有查询到任何数据";
		return ResponseMessage.success(message, Collections.singletonMap("adminPage", adminPage));
	}

	@PostMapping("/main/user")
	@ResponseBody
	public ResponseMessage<?,?> saveAdmin(Admin admin){
		service.save(admin);
		return ResponseMessage.success("保存成功！");
	}

	@DeleteMapping("/main/user/{id}")
	@ResponseBody
	public ResponseMessage<?,?> deleteAdmin(@PathVariable("id") int id){
		boolean result = service.removeById(id);
		if(result) return ResponseMessage.success("删除成功！");
		return ResponseMessage.failure("删除失败！");
	}
}

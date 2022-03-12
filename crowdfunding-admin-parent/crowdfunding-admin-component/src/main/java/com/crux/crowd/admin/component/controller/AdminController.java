package com.crux.crowd.admin.component.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crux.crowd.admin.component.service.AdminService;
import com.crux.crowd.admin.entity.Admin;
import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.common.util.ResponseMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
	public ResponseMessage<String,?> getAdminPage(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
							   @RequestParam(name = "current") int current,
							   @RequestParam(name = "size", required = false, defaultValue = "10") int size){
		Page<Admin> adminPage = service.pageFuzzy(keyword, current, size);
		String message = "查询到" + adminPage.getTotal() + "条数据";
		Map<String,Object> data = new HashMap<>();
		data.put("adminPage", adminPage);
		if(!keyword.isEmpty()) data.put("keyword", keyword);
		return ResponseMessage.success(message, data);
	}

	@DeleteMapping("/main/user/{id}")
	@ResponseBody
	public ResponseMessage<?,?> deleteAdmin(@PathVariable("id") int id){
		boolean result = service.removeById(id);
		if(result) return ResponseMessage.success("删除成功！");
		return ResponseMessage.failure("删除失败！");
	}
}

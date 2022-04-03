package com.crux.crowd.member.controller;

import static com.crux.crowd.common.util.CrowdConstant.*;
import com.crux.crowd.common.util.ResponseResult;
import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.api.DataSourceRemoteService;
import com.crux.crowd.member.api.RedisRemoteService;
import com.crux.crowd.member.api.SendMessageRemoteService;
import com.crux.crowd.member.entity.po.MemberPO;
import com.crux.crowd.member.entity.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController{

	private SendMessageRemoteService messageRemoteService;
	private RedisRemoteService redisRemoteService;
	private DataSourceRemoteService dataSourceRemoteService;
	private PasswordEncoder passwordEncoder;

	/**
	 * 注册账号，发送短信验证码
	 * @param phone 手机号码
	 * @return 执行结果
	 */
	@PostMapping("/register/sendMessage")
	public ResultEntity<?,?> sendMessage(String phone,
										 @CookieValue(name = COOKIE_MESSAGE_CODE_INTERVAL, required = false, defaultValue = "-1") long interval,
										 HttpServletResponse response){
		// 1、检查cookie是否过期
		long time = interval - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
		if(time > 0){
			return ResultEntity.failure("请" + time + "秒后重试");
		}

		// 2、发送短信验证码，并获取执行结果
		ResultEntity<String,String> messageResult = ResultEntity.success("", Collections.singletonMap("code", "111111"));
													// messageRemoteService.sendMessage(phone, codeTimeout);
		// 3、检查是否发送成功
		if(messageResult.getResult() != ResponseResult.SUCCESS){
			return ResultEntity.error(messageResult.getMessage());
		}

		// 4、发送成功，存入redis中
		String key = REDIS_MESSAGE_CODE_PREFIX + phone;
		// key=message-code:phone,value=code,timeout=3 minutes
		ResultEntity<?,?> redisResult = redisRemoteService.set(key, messageResult.getData().get("code"), DEFAULT_MESSAGE_TIMEOUT_MINUTES, TimeUnit.MINUTES);
		// 5、检查是否存入成功
		if(redisResult.getResult() == ResponseResult.FAILURE){
			return ResultEntity.error(redisResult.getMessage());
		}

		// 6、执行成功，保存cookie
		interval = LocalDateTime.now().plusMinutes(1).toEpochSecond(ZoneOffset.UTC);
		Cookie intervalCookie = new Cookie(COOKIE_MESSAGE_CODE_INTERVAL, Long.toString(interval));
		intervalCookie.setMaxAge(60);
		intervalCookie.setPath("/member/register/sendMessage");
		response.addCookie(intervalCookie);

		return ResultEntity.success("发送成功！请注意查收");
	}

	@GetMapping(params = "login_acct")
	public ResultEntity<String,MemberPO> getByLoginAcct(@RequestParam("login_acct") String loginAcct){
		return dataSourceRemoteService.getMemberByLoginAcct(loginAcct);
	}

	/**
	 * 执行注册
	 * @param memberVO member注册表单数据
	 * @return 执行结果
	 */
	@PostMapping("/register")
	public ResultEntity<?,?> register(MemberVO memberVO){
		// 1、获取手机号
		String phone = memberVO.getPhone();
		// 2、从redis中获取该手机号对应的验证码，并校验
		String key = REDIS_MESSAGE_CODE_PREFIX + phone;
		String code = memberVO.getCode();
		ResultEntity<String,String> redisResult = redisRemoteService.get(key);
		// 2.1、如果没有查询到数据，提示验证码已失效
		if(redisResult.getResult() == ResponseResult.FAILURE){
			return ResultEntity.failure(TipsMessage.CODE_TIMEOUT);
		}
		// 2.2、如果发送的验证码与提交的验证码不一致，提示验证码不一致
		if(redisResult.getResult() == ResponseResult.SUCCESS && !redisResult.getData().get(key).equals(code)){
			return ResultEntity.failure(TipsMessage.CODE_INCONSISTENT);
		}
		// 3、验证码一致，将redis中存储的手机号删除
		redisRemoteService.delete(key);
		// 4、对密码加密
		String password = passwordEncoder.encode(memberVO.getUserPswd());
		memberVO.setUserPswd(password);
		// 5、转换为PO
		MemberPO memberPO = new MemberPO();
		BeanUtils.copyProperties(memberVO, memberPO, StringUtils.hasLength(memberVO.getUserName()) ? null : "userName");

		ResultEntity<?,?> saveResult = dataSourceRemoteService.saveMember(memberPO);
		if(saveResult.getResult() == ResponseResult.SUCCESS){
			saveResult = ResultEntity.success("注册成功！5秒后将跳转到登录页面");
		}
		return saveResult;
	}

	@Autowired
	public void setMessageService(SendMessageRemoteService messageRemoteService){
		this.messageRemoteService = messageRemoteService;
	}

	@Autowired
	public void setRedisRemoteService(RedisRemoteService redisRemoteService){
		this.redisRemoteService = redisRemoteService;
	}

	@Autowired
	public void setDataSourceRemoteService(DataSourceRemoteService dataSourceRemoteService){
		this.dataSourceRemoteService = dataSourceRemoteService;
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder){
		this.passwordEncoder = passwordEncoder;
	}
}

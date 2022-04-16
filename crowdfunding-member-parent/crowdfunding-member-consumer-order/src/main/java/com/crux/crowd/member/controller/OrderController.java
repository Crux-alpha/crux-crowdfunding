package com.crux.crowd.member.controller;

import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.common.util.ResponseResult;
import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.api.DataSourceRemoteService;
import com.crux.crowd.member.entity.vo.OrderProjectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class OrderController{

	private final DataSourceRemoteService dataSourceRemoteService;

	public OrderController(DataSourceRemoteService dataSourceRemoteService){
		this.dataSourceRemoteService = dataSourceRemoteService;
	}

	/**
	 * <h3>支持页面步骤1</h3>
	 * 根据回报id查询所需展示的数据，并返回页面
	 * @param returnId 回报id
	 * @return order.html
	 */
	@RequestMapping(path = "/pay", params = "returnId")
	public ModelAndView orderPay(@RequestParam("returnId") Integer returnId, ModelAndView mav, HttpSession session){
		ResultEntity<String,OrderProjectVO> result = dataSourceRemoteService.getOrderProject(returnId);
		if(ResponseResult.SUCCESS.equalsResultEntity(result)){
			mav.setViewName("order");
			result.getData().forEach(session::setAttribute);
		}else{
			mav.setStatus(HttpStatus.NOT_FOUND);
			mav.setViewName("error");
			mav.addObject(CrowdConstant.MESSAGE, result.getMessage());
		}
		return mav;
	}

}

package com.crux.crowd.member.controller;

import static com.crux.crowd.common.util.CrowdConstant.*;

import com.crux.crowd.common.util.ResponseResult;
import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.api.DataSourceRemoteService;
import com.crux.crowd.member.entity.vo.AddressVO;
import com.crux.crowd.member.entity.vo.MemberInfoVO;
import com.crux.crowd.member.entity.vo.OrderProjectVO;
import com.crux.crowd.member.entity.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
			mav.addObject(MESSAGE, result.getMessage());
		}
		return mav;
	}

	/**
	 * <h3>支持页面步骤2</h3>
	 * 保存支持数量，查询当前用户的收货地址信息并返回
	 * @param returnCount 回报数量
	 * @return 当前用户的所有收货地址
	 */
	@PostMapping("/pay/{return_count}")
	@ResponseBody
	public ResultEntity<Integer,AddressVO> toCloseAnAccount(@PathVariable("return_count") Integer returnCount, HttpSession session){
		Object memberInfo = session.getAttribute(SESSION_ATTRIBUTE_MEMBER_INFO);
		Object orderProject = session.getAttribute(SESSION_ATTRIBUTE_ORDER_PROJECT);

		// 1、如果当前保存的用户信息和订单信息无效，则提示刷新页面
		if(memberInfo instanceof MemberInfoVO && orderProject instanceof OrderProjectVO){
			// 2、更新数量
			OrderProjectVO orderProjectVO = (OrderProjectVO)orderProject;
			orderProjectVO.setReturnCount(returnCount);
			session.removeAttribute(SESSION_ATTRIBUTE_ORDER_PROJECT);
			session.setAttribute(SESSION_ATTRIBUTE_ORDER_PROJECT, orderProjectVO);

			// 3、查询地址，返回客户端
			return dataSourceRemoteService.getAddresses(((MemberInfoVO)memberInfo).getId());

		}else return ResultEntity.failure(TipsMessage.HTML_FAILURE);
	}

	/**
	 * 保存一个收货地址，同时再返回该用户所有收货地址
	 * @param addressVO 一个收货地址
	 * @return 当前用户的所有收货地址
	 * @see #toCloseAnAccount(Integer, HttpSession)
	 */
	@PostMapping("/pay/address/save")
	@ResponseBody
	public ResultEntity<Integer,AddressVO> saveAndGetAddress(AddressVO addressVO){
		return addressVO.getMemberId() == null ?
				ResultEntity.failure(TipsMessage.HTML_FAILURE) :
				dataSourceRemoteService.saveAndGetAddress(addressVO);
	}

	/**
	 * 删除一个订单
	 * @param orderNum 订单号
	 * @return 执行结果
	 */
	@DeleteMapping("/remove/{orderNum}")
	@ResponseBody
	public ResultEntity<?,?> removeOrder(@PathVariable("orderNum") String orderNum, HttpSession session){
		Object memberInfo = session.getAttribute(SESSION_ATTRIBUTE_MEMBER_INFO);
		if(memberInfo instanceof MemberInfoVO){
			return dataSourceRemoteService.removeOrder(orderNum, ((MemberInfoVO)memberInfo).getId());
		}
		return ResultEntity.failure(TipsMessage.HTML_FAILURE);
	}

	@GetMapping("/get/{orderNum}")
	@ResponseBody
	public ResultEntity<String,OrderVO> getOrder(@PathVariable("orderNum") String orderNum){
		return dataSourceRemoteService.getOrder(orderNum);
	}
}

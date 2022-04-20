package com.crux.crowd.member.controller;

import com.alipay.api.AlipayApiException;
import com.aliyun.oss.ServiceException;
import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.common.util.ResponseResult;
import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.api.AlipayRemoteService;
import com.crux.crowd.member.api.DataSourceRemoteService;
import com.crux.crowd.member.api.RedisRemoteService;
import com.crux.crowd.member.entity.vo.OrderProjectVO;
import com.crux.crowd.member.entity.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Consumer;

import static com.crux.crowd.common.util.CrowdConstant.*;
import static java.nio.charset.StandardCharsets.*;

/**
 * 支付模块
 * @since 2022-04-18
 */
@Slf4j
@RestController
public class PayController{

	private final AlipayRemoteService alipayRemoteService;
	private final DataSourceRemoteService dataSourceRemoteService;
	private final RedisRemoteService redisRemoteService;

	public PayController(AlipayRemoteService alipayRemoteService, DataSourceRemoteService dataSourceRemoteService, RedisRemoteService redisRemoteService){
		this.alipayRemoteService = alipayRemoteService;
		this.dataSourceRemoteService = dataSourceRemoteService;
		this.redisRemoteService = redisRemoteService;
	}


	/**
	 * <h3>支持页面步骤3</h3>
	 * 立即结账，将数据收集并生成订单，返回由支付宝api生成的订单页面
	 * @param orderVO 当前订单信息
	 * @throws AlipayApiException 如果出现任何调用支付宝API产生的问题
	 */
	@PostMapping("/order/support_project")
	public String supportProjectOrder(OrderVO orderVO, HttpSession session) throws AlipayApiException{
		Object orderProject = session.getAttribute(SESSION_ATTRIBUTE_ORDER_PROJECT);
		if(orderProject instanceof OrderProjectVO){
			OrderProjectVO op = (OrderProjectVO)orderProject;
			// 1、将项目信息保存到order
			orderVO.setOrderProjectVO(op);

			// 2、为order生成订单号
			String instant = LocalDateTime.now().format(NUMBER_TIME_FORMAT);
			String uid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
			orderVO.setOrderNum(instant + uid);

			// 3、先存入redis，最后根据结果修改数据，再存入数据库。此时订单没有支付宝交易号和付款金额
			session.removeAttribute(SESSION_ATTRIBUTE_ORDER_PROJECT);
			// ResultEntity<?,?> result = dataSourceRemoteService.saveOrder(orderVO);
			ResultEntity<?,?> result = redisRemoteService.saveOrder(orderVO);

			// 4、计算所需支付金额
			BigDecimal price = op.getSupportPrice();	// 单价
			BigDecimal count = BigDecimal.valueOf(op.getReturnCount());	// 购买数量
			BigDecimal freight = op.getFreight();	// 运费
			BigDecimal orderAmount = price.multiply(count).add(freight);	// 需支付金额

			return ResponseResult.SUCCESS.equalsResultEntity(result) ?
				// 4、调用支付宝API，生成支付页面(返回的是html标签文本)
				alipayRemoteService.alipayOrderPage(
						orderVO.getOrderNum(),
						orderAmount.toString(),
						op.getProjectName(),
						orderVO.getOrderRemark())
			: TipsMessage.SERVER_ERROR;
		}else
			return CrowdConstant.TipsMessage.HTML_FAILURE;
	}

	/**
	 * 支付宝支付成功后发送请求到此方法，用来响应客户端支付成功的页面
	 * @return 支付成功页面
	 */
	@GetMapping("/alipay/return")
	public String returnUrl(HttpServletRequest request) throws AlipayApiException{
		//——请在这里编写您的程序（以下代码仅作参考）——
		if(!alipayRemoteService.signVerified(request.getParameterMap())) throw new ServiceException("验签失败");

		//商户订单号
		String orderNum = new String(request.getParameter("out_trade_no").getBytes(ISO_8859_1), UTF_8);
		//支付宝交易号
		String payOrderNum = new String(request.getParameter("trade_no").getBytes(ISO_8859_1), UTF_8);
		//付款金额
		double orderAmount = Double.parseDouble(new String(request.getParameter("total_amount").getBytes(ISO_8859_1), UTF_8));

		return "商户订单号：" + orderNum + "<br/>" + "支付宝交易号：" + payOrderNum + "<br/>" + "付款金额：" + orderAmount;
	}

	/**
	 * 支付宝支付成功后发送请求到此方法，实际支付结果以该方法为准
	 */
	@PostMapping("/alipay/notify")
	public void notifyUrl(HttpServletRequest request){
		Consumer<ResultEntity<?,?>> checkResult = result -> {
			if(!ResponseResult.SUCCESS.equalsResultEntity(result)){
				throw new ServiceException(result.getMessage());
			}
		};
		//——请在这里编写您的程序（以下代码仅作参考）——
		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
		//商户订单号
		String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(ISO_8859_1), UTF_8);
		//支付宝交易号
		String tradeNo = new String(request.getParameter("trade_no").getBytes(ISO_8859_1), UTF_8);
		//付款金额
		String orderAmount = new String(request.getParameter("total_amount").getBytes(ISO_8859_1), UTF_8);
		//交易状态
		String tradeStatus = new String(request.getParameter("trade_status").getBytes(ISO_8859_1), UTF_8);

		log.info("当前交易状态 ==> 商户订单号:{}, 支付宝交易号:{}, 交易金额:{}, 交易状态:{}", outTradeNo, tradeNo, orderAmount, tradeStatus);
		// 如果交易成功，更新项目筹集状态
		if("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus)){
			/* 1、后台'支付'订单
			ResultEntity<?,?> payResult = dataSourceRemoteService.payOrder(outTradeNo, tradeNo, orderAmount);
			checkResult.accept(payResult);
			*/

			// 1、从redis中获取该订单
			ResultEntity<String,OrderVO> getResult = redisRemoteService.getOrder(outTradeNo);
			checkResult.accept(getResult);

			OrderVO order = getResult.getData().get("order");
			// 2、设置支付宝流水号、订单金额
			order.setPayOrderNum(tradeNo);
			order.setOrderAmount(new BigDecimal(orderAmount));

			// 3、保存到数据库
			checkResult.accept(dataSourceRemoteService.saveOrder(order));

			// 4、后台'支持'项目
			ResultEntity<?,?> supportProjectResult = dataSourceRemoteService.supportProject(outTradeNo, Double.parseDouble(orderAmount));
			checkResult.accept(supportProjectResult);

			log.info("订单号为：{} 的订单已成功保存！", outTradeNo);
		}else{
			log.warn("订单号为：{} 的订单支付失败！", outTradeNo);
		}
	}
}
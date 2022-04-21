package com.crux.crowd.member.api;

import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.entity.vo.OrderVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

/**
 * redis远程服务
 */
@FeignClient(name = "crowd-redis")
public interface RedisRemoteService{

	/**
	 * set 'key' 'value'
	 * @param timeout 超时时长。
	 * @return 执行结果
	 */
	@PostMapping("/set")
	ResultEntity<?,?> set(@RequestParam(name = "key") String key,
						  @RequestParam(name = "value") String value,
						  @RequestParam(name = "expire", required = false) Duration timeout);

	/**
	 * get 'key'
	 * @return value
	 */
	@GetMapping("/get")
	ResultEntity<String,String> get(@RequestParam(name = "key") String key);

	/**
	 * del 'key'
	 * @return 执行结果
	 */
	@DeleteMapping("/delete")
	ResultEntity<?,?> delete(@RequestParam(name = "key") String key);


	/**
	 * 保存一个已确认但尚未支付的订单信息
	 * @param orderVO 确认订单信息
	 * @return 执行结果
	 */
	@PostMapping("/order")
	ResultEntity<?,?> saveOrder(@RequestBody OrderVO orderVO);

	/**
	 * 取出订单
	 * @param orderNum 订单号
	 * @return {@link OrderVO}
	 */
	@GetMapping("/order/{orderNum}")
	ResultEntity<String,OrderVO> getOrder(@PathVariable("orderNum") String orderNum);
}

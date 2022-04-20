package com.crux.crowd.member.controller;

import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.entity.vo.OrderProjectVO;
import com.crux.crowd.member.entity.vo.OrderVO;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <h2>订单服务提供者</h2>
 * 可以将订单临时存入redis，在支付成功后存入持久层
 * @since 2022-04-20
 */
@RestController
@RequestMapping("/order")
public class OrderProviderController{

    private final StringRedisTemplate redisTemplate;
    static final String ORDER_NUM_PROJECT_KEY_SUFFIX = ":project";

    public OrderProviderController(StringRedisTemplate redisTemplate){this.redisTemplate = redisTemplate;}

    @PostMapping
    public ResultEntity<?,?> saveOrder(@RequestBody OrderVO order){
        String orderKey = order.getOrderNum();
        String orderProjectKey = orderKey + ORDER_NUM_PROJECT_KEY_SUFFIX;
        
        OrderProjectVO orderProject = order.getOrderProjectVO();

        Map<String,String> orderHash = new HashMap<>();
        orderHash.put("addressId", order.getAddressId().toString());
        orderHash.put("invoice", order.getInvoice().toString());
        orderHash.put("invoiceTitle", order.getInvoiceTitle());
        orderHash.put("orderRemark", order.getOrderRemark());

        Map<String,String> orderProjectHash = new HashMap<>();
        orderProjectHash.put("projectName", orderProject.getProjectName());
        orderProjectHash.put("launchName", orderProject.getLaunchName());
        orderProjectHash.put("returnContent", orderProject.getReturnContent());
        orderProjectHash.put("returnCount", orderProject.getReturnCount().toString());
        orderProjectHash.put("supportPrice", orderProject.getSupportPrice().toString());
        orderProjectHash.put("freight", orderProject.getFreight().toString());
        orderProjectHash.put("returnId", orderProject.getReturnId().toString());

        HashOperations<String,String,String> hashOps = redisTemplate.opsForHash();
        hashOps.putAll(orderKey, orderHash);
        hashOps.putAll(orderProjectKey, orderProjectHash);

        // 在redis中默认保存48小时
        redisTemplate.expire(orderKey, CrowdConstant.REDIS_ORDER_DEFAULT_EXPIRE);
        redisTemplate.expire(orderProjectKey, CrowdConstant.REDIS_ORDER_DEFAULT_EXPIRE);
        return ResultEntity.success();
    }

    @GetMapping("/{orderNum}")
    public ResultEntity<String,OrderVO> getOrder(@PathVariable("orderNum") String orderNum){

        HashOperations<String,String,String> hashOps = redisTemplate.opsForHash();
        Map<String,String> orderHash = hashOps.entries(orderNum);
        OrderVO order = new OrderVO(
                Integer.parseInt(orderHash.get("addressId")),
                Boolean.valueOf(orderHash.get("invoice")),
                orderHash.get("invoiceTitle"),
                orderHash.get("orderRemark"));
        order.setOrderNum(orderNum);

        Map<String,String> orderProjectHash = hashOps.entries(orderNum + ORDER_NUM_PROJECT_KEY_SUFFIX);
        OrderProjectVO orderProject = new OrderProjectVO(
                orderProjectHash.get("projectName"),
                orderProjectHash.get("launchName"),
                orderProjectHash.get("returnContent"),
                Integer.parseInt(orderProjectHash.get("returnCount")),
                new BigDecimal(orderProjectHash.get("supportPrice")),
                new BigDecimal(orderProjectHash.get("freight")),
                null,
                null,
                null,
                Integer.parseInt(orderProjectHash.get("returnId"))
        );
        order.setOrderProjectVO(orderProject);

        return ResultEntity.success(Collections.singletonMap("order", order));
    }
}

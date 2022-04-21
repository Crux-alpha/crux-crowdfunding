package com.crux.crowd.member.service.task;

import com.crux.crowd.common.util.CrowdConstant;
import com.crux.crowd.member.entity.po.OrderPO;
import com.crux.crowd.member.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OrderTask{

    private final OrderService orderService;
    private final String taskName = getClass().getSimpleName();

    @Value("${crowd.task.order.expire-days}")
    private int expireDays;

    public OrderTask(OrderService orderService){
        log.info("[守护线程]  {}定时器已开启！将于每周末4:00清理订单", taskName);
        this.orderService = orderService;
    }

    /**
     * 每周日的4:00执行清理
     */
    @Scheduled(cron = "0 0 4 * * 1")
    public void clear(){
        log.info(" ==>{}：准备清理订单！<==", taskName);

        List<OrderPO> list = orderService.list();
        List<Integer> expireOrderIds = list.stream()
                .filter(this::isExpired)
                .map(OrderPO::getId)
                .collect(Collectors.toList());

        if(!expireOrderIds.isEmpty()){
            boolean result = orderService.removeBatchByIds(expireOrderIds);
            if(!result){
                log.error(" ==>{}：清理订单时出错，请检查服务器！<==", taskName);
                return;
            }
        }
        log.info(" ==>{}：清理完成！<==", taskName);
    }

    private boolean isExpired(OrderPO order){
        String orderNum = order.getOrderNum();
        String timeString = orderNum.substring(0, 14);
        LocalDateTime expireTime = LocalDateTime.parse(timeString, CrowdConstant.NUMBER_TIME_FORMAT).plusDays(expireDays);
        return LocalDateTime.now().isAfter(expireTime);
    }
}

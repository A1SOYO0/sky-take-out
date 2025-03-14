package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    public OrderTask(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    //处理支付超时订单
    @Scheduled(cron = "0 * * * * ?")//每分钟一次
    public void processTimeoutOrder() {
        LocalDateTime now = LocalDateTime.now();
        log.info("定时处理超时订单：{}", now);
        //查询是否有超时订单
        List<Orders> list = orderMapper.getByStatusAndTime(Orders.PENDING_PAYMENT,now.minusMinutes(15));
        if(list != null && !list.isEmpty()) {
            for (Orders order : list) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时，自动取消");
                order.setCancelTime(now);
                orderMapper.update(order);
            }
        }
    }

    //定时处理派送中订单
    @Scheduled(cron = "5 * * * * ?")
    public void processDeliveryOrder(){
        LocalDateTime now = LocalDateTime.now();
        log.info("定时处理一直处于派送中的订单：{}", now);
        List<Orders> list = orderMapper.getByStatusAndTime(Orders.DELIVERY_IN_PROGRESS,now.minusHours(1));
        if(list != null && !list.isEmpty()) {
            for (Orders order : list) {
                order.setStatus(Orders.COMPLETED);
                order.setDeliveryTime(now);
                orderMapper.update(order);
            }
        }
    }
}

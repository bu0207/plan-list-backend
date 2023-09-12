package com.bnt.plan.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * http服务调用
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/8/30 17:30 bnt
 * @history
 */
@FeignClient(name = "plan-list-core", fallback = OrderFeignHandler.class)
@Component
public interface OrderFeign {
    @GetMapping("core/order/getOrderNo")
    String getOrderNo(@RequestParam String userId);
}

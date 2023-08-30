package com.bnt.user.feign;

import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/8/30 17:50 bnt
 * @history
 */
@Component
public class OrderFeignFallback implements OrderFeign {
    @Override
    public String getOrderNo(String userId) {
        return null;
    }
}

package com.bnt.user.feign;

import org.springframework.stereotype.Component;

/**
 * OrderFeign容错处理
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/8/31 15:19 bnt
 * @history
 */
@Component
public class OrderFeignHandler implements OrderFeign {
    @Override
    public String getOrderNo(String userId) {
        return "当前人数过多,休息一会再试";
    }
}

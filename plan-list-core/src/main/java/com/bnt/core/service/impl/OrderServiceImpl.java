package com.bnt.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.bnt.core.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/8/30 17:12 bnt
 * @history
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public String getOrderNo(String userId) {
        if (StrUtil.isNotEmpty(userId) && userId.equals("mdx123456")) {
            return "O111222333444";
        }
        return "订单不存在";
    }
}

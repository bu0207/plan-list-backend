package com.bnt.core.controller;

import com.bnt.common.common.BaseResponse;
import com.bnt.common.common.ResultUtils;
import com.bnt.core.service.OrderService;
import com.bnt.core.service.OrderTblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderTblService orderTblService;

    @GetMapping("getOrderNo")
    public String getOrderNo(String userId){
        return orderService.getOrderNo(userId);
    }

    /**
     * 测试负载均衡
     *
     * @return
     */
    @GetMapping("lb")
    public String lb() {
        System.out.println("test lb");
        return "lb";
    }

    /**
     * 用户下单接口
     * @param userId
     * @param commodityCode
     * @return
     */
    @GetMapping ("createOrder")
    public BaseResponse<String> createOrder(String userId, String commodityCode){
        return ResultUtils.success(orderTblService.createOrder(userId,commodityCode));
    }
}


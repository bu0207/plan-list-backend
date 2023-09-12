package com.bnt.plan.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.bnt.plan.sentinelhandler.UserSentinelResourceHandler;
import com.bnt.plan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试类
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/8/30 11:34 bnt
 * @history
 */
@RestController
@RequestMapping("/hello")
@RefreshScope
public class HelloWordController {

    @Value("${user.switch_on_off}")
    private String switchOnOff;

    @Autowired
    private UserService userService;


    @GetMapping("getUserSwitch")
    public Object getUserSwitch() {
        return switchOnOff != null ? switchOnOff : "未获取到配置";
    }

    @GetMapping("getOrderNo")
    @SentinelResource(value = "getOrderNoResource", blockHandler = "getOrderNoBlockHandler", blockHandlerClass = HelloWordController.class)
    public String getOrderNo(String userId) {
        return userService.getOrderNo(userId);
    }

    /**
     * 限流后续操作方法
     *
     * @param e
     * @return
     */
    public static String getOrderNoBlockHandler(String userId, BlockException e) {
        String msg = "不好意思，前方拥挤，请您稍后再试";
        return msg;
    }

    @GetMapping("sentinelB")
    public String sentinelB() {
        return "我是关联接口";
    }

    @GetMapping("sentinelA")
    @SentinelResource(value = "sentinelAResource", fallback = "sentinelAResource", fallbackClass = UserSentinelResourceHandler.class)
    public String sentinelA() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我是sentinelA");
        return "我是sentinelA";
    }

    /**
     * 测试centinel热点规则限流
     *
     * @param userId
     * @param shopId
     * @return
     */
    @GetMapping("/hotspot")
    @SentinelResource(value = "hotspotResource", blockHandler = "hotspotResource", blockHandlerClass = UserSentinelResourceHandler.class)
    public String hotspot(@RequestParam(value = "userId", required = false) String userId,
                          @RequestParam(value = "shopId", required = false) String shopId) {
        System.out.println("我是hotspot");
        return "我是hotspot";
    }
}

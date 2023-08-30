package com.bnt.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("getUserSwitch")
    public Object getUserSwitch() {
        return switchOnOff != null ? switchOnOff : "未获取到配置";
    }
}

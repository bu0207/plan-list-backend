package com.bnt.plan.controller;

import com.bnt.plan.common.BaseResponse;
import com.bnt.plan.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/10/13 15:59 bnt
 * @history
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/echo")
    public String demo() {
        return "示例返回";
    }

    @GetMapping("/home")
    public String home() {
        return "我是首页";
    }

    @GetMapping("/admin")
    public String admin() {
        return "我是管理员";
    }

    @GetMapping("/normal")
    public String normal() {
        return "我是普通用户";
    }

    @GetMapping("/res")
    public BaseResponse res() {
        return ResultUtils.success("响应成功");
    }
}

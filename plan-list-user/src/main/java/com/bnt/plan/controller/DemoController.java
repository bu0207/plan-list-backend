package com.bnt.plan.controller;

import com.bnt.plan.common.BaseResponse;
import com.bnt.plan.common.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

/**
 * TODO
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/10/20 10:16 bnt
 * @history
 */
@Api(tags = "测试权限")
@RestController
@RequestMapping("/demo")
public class DemoController {
    @PermitAll
    @GetMapping("/echo")
    public String demo() {
        return "示例返回";
    }

    @GetMapping("/home")
    public String home() {
        return "我是首页";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "我是管理员";
    }

    @PreAuthorize("hasRole('ROLE_NORMAL')")
    @GetMapping("/normal")
    public String normal() {
        return "我是普通用户";
    }

    @ApiOperation(value = "echo",httpMethod = "GET")
    @GetMapping("/echo1")
    @PreAuthorize("@ss.hasPermi('user:test:echo')")
    public String demo1() {
        return "示例返回";
    }

    @GetMapping("/home1")
    @PreAuthorize("@ss.hasPermi('user:test:echo')")
    public String home1() {
        return "我是首页";
    }

    @GetMapping("/admin1")
    public String admin1() {
        return "我是管理员";
    }

    @GetMapping("/normal1")
    public String normal1() {
        return "我是普通用户";
    }

    @GetMapping("/res1")
    public BaseResponse res1() {
        return ResultUtils.success("响应成功");
    }
}

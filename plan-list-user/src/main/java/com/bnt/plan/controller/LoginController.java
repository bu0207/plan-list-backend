package com.bnt.plan.controller;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.bnt.plan.common.BaseResponse;
import com.bnt.plan.common.ResultUtils;
import com.bnt.plan.model.dto.user.UserInfoRes;
import com.bnt.plan.model.dto.user.UserLoginRequest;
import com.bnt.plan.model.entity.SysUser;
import com.bnt.plan.userdetail.LoginUser;
import com.bnt.plan.userdetail.service.SysLoginService;
import com.bnt.plan.userdetail.service.SysPermissionService;
import com.bnt.plan.userdetail.service.TokenService;
import com.bnt.plan.utils.ServletUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

/**
 * 登录
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/9/22 15:46 bnt
 * @history
 */
@Api(tags = "登录")
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private SysLoginService sysLoginService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Value("${public.aesKey}")
    private String aesKey;

    @ApiOperation(value = "登录", httpMethod = "POST")
    @PostMapping("login")
    public BaseResponse login(@Valid @RequestBody UserLoginRequest loginRequest) {
        String login = sysLoginService.login(loginRequest);
        return ResultUtils.success(login);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @ApiOperation(value = "用户信息", httpMethod = "GET")
    @GetMapping("/getInfo")
    public BaseResponse getInfo() {
        // <1> 获得当前 LoginUser
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        AES aes = SecureUtil.aes(aesKey.getBytes());
        String userName = aes.encryptHex(user.getUserName());
        user.setUserName(userName);
        // <2> 角色标识的集合
        Set<String> rolePermission = sysPermissionService.getRolePermission(user);
        // <3> 权限集合
        Set<String> menuPermission = sysPermissionService.getMenuPermission(user);
        return ResultUtils.success(new UserInfoRes(user, rolePermission, menuPermission));
    }
}

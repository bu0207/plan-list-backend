package com.bnt.plan.controller;

import com.bnt.plan.common.BaseResponse;
import com.bnt.plan.common.ResultUtils;
import com.bnt.plan.constant.CommonConstant;
import com.bnt.plan.model.dto.user.UserLoginRequest;
import com.bnt.plan.model.vo.LoginUserVO;
import com.bnt.plan.service.SysUserService;
import com.bnt.plan.userdetail.service.SysLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/9/22 15:46 bnt
 * @history
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private SysUserService userService;

    @Autowired
    private SysLoginService sysLoginService;

    /**
     * 登录
     *
     * @return
     */
    @PostMapping("loginByPas")
    public BaseResponse<LoginUserVO> loginByPas(@RequestBody UserLoginRequest loginRequest) {
        return ResultUtils.success(userService.loginByPas(loginRequest));
    }

    @PostMapping("login")
    public BaseResponse login(@RequestBody UserLoginRequest loginRequest) {
        BaseResponse<Object> success = ResultUtils.success();
        String login = sysLoginService.login(loginRequest);
        success.put(CommonConstant.TOKEN, login);
        return success;
    }
}

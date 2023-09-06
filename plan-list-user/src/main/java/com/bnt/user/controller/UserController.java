package com.bnt.user.controller;


import com.bnt.common.common.BaseResponse;
import com.bnt.common.common.ResultUtils;
import com.bnt.user.model.dto.user.UserLoginRequest;
import com.bnt.user.model.vo.LoginUserVO;
import com.bnt.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bnt
 * @since 2023-09-06
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录
     * @return
     */
    @PostMapping("login")
    public BaseResponse<LoginUserVO> login(@RequestBody UserLoginRequest loginRequest) {
        return ResultUtils.success(userService.login(loginRequest));
    }
}

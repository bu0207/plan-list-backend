package com.bnt.user.model.dto.user;

import lombok.Data;

/**
 * 用户登录请求
 */
@Data
public class UserLoginRequest {

    private String userName;

    private String userPassword;
}

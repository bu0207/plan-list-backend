package com.bnt.plan.model.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录请求
 */
@Data
public class UserLoginRequest {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String userPassword;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "唯一标识")
    private String uuid;
}

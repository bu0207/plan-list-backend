package com.bnt.plan.service;

import com.bnt.plan.model.dto.user.UserLoginRequest;
import com.bnt.plan.model.vo.LoginUserVO;

/**
 * 用户服务
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/8/30 17:13 bnt
 * @history
 */
public interface UserService {
    public String getOrderNo(String userId);

    public LoginUserVO login(UserLoginRequest loginRequest);
}

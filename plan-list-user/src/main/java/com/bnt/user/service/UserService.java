package com.bnt.user.service;

import com.bnt.user.model.dto.user.UserLoginRequest;
import com.bnt.user.model.vo.LoginUserVO;

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

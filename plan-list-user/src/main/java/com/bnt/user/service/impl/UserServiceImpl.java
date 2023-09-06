package com.bnt.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnt.user.feign.OrderFeign;
import com.bnt.user.mapper.UserMapper;
import com.bnt.user.model.dto.user.UserLoginRequest;
import com.bnt.user.model.entity.User;
import com.bnt.user.model.vo.LoginUserVO;
import com.bnt.user.service.UserService;
import com.bnt.user.utils.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/8/30 17:12 bnt
 * @history
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private JWTProvider jwtProvider;

    @Value("${jwt.prefix}")
    private String prefix;

    @Override
    public String getOrderNo(String userId) {
        return orderFeign.getOrderNo(userId);
    }

    @Override
    public LoginUserVO login(UserLoginRequest loginRequest) {
        User user = baseMapper.findByUserName(loginRequest.getUserName());
        return null;
    }
}

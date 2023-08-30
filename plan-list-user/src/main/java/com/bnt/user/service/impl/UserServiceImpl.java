package com.bnt.user.service.impl;

import com.bnt.user.feign.OrderFeign;
import com.bnt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private OrderFeign orderFeign;

    @Override
    public String getOrderNo(String userId) {
        return orderFeign.getOrderNo(userId);
    }
}

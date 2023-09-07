package com.bnt.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnt.common.constant.UserConstant;
import com.bnt.common.exception.BusinessException;
import com.bnt.user.feign.OrderFeign;
import com.bnt.user.mapper.UserMapper;
import com.bnt.user.model.dto.user.UserLoginRequest;
import com.bnt.user.model.entity.User;
import com.bnt.user.model.vo.LoginUserVO;
import com.bnt.user.service.RedisService;
import com.bnt.user.service.UserService;
import com.bnt.user.utils.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Value("${jwt.prefix}")
    private String prefix;

    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private RedisService redisService;

    @Override
    public String getOrderNo(String userId) {
        return orderFeign.getOrderNo(userId);
    }

    @Override
    public LoginUserVO login(UserLoginRequest loginRequest) {
        String userName = loginRequest.getUserName();
        User user = baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserName, userName));
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 判断用户名密码是否正确
        if (StrUtil.isEmpty(userName) ||
                !encoder.matches(loginRequest.getUserPassword(), user.getPassword())) {
            throw new BusinessException("用户名或者密码错误");
        }
        // 生成token
        String token = jwtProvider.generateToken(loginRequest.getUserName());

        // 将token存入redis
        redisService.set(UserConstant.USER_TOKEN_KEY_REDIS + userName, token, 604800);

        return LoginUserVO.builder().userName(userName).id(user.getUserId()).token(prefix + " " + token).build();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode("admin");
        System.out.println(password);
    }
}

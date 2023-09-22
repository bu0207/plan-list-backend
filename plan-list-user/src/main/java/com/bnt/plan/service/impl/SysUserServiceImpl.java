package com.bnt.plan.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnt.plan.constant.UserConstant;
import com.bnt.plan.exception.BusinessException;
import com.bnt.plan.feign.OrderFeign;
import com.bnt.plan.mapper.SysUserMapper;
import com.bnt.plan.model.dto.user.UserLoginRequest;
import com.bnt.plan.model.entity.SysUser;
import com.bnt.plan.model.vo.LoginUserVO;
import com.bnt.plan.service.RedisService;
import com.bnt.plan.service.SysUserService;
import com.bnt.plan.utils.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author bnt
 * @since 2023-09-22
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Value("${jwt.prefix}")
    private String prefix;

    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private RedisService redisService;

    @Override
    public LoginUserVO loginByPas(UserLoginRequest loginRequest) {
        String userName = loginRequest.getUserName();
        SysUser user = baseMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, userName));
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

        return LoginUserVO.builder().userName(userName).id(user.getId()).token(prefix + " " + token).build();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode("admin");
        System.out.println(password);
    }
}

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
import com.bnt.plan.userdetail.service.TokenService;
import com.bnt.plan.utils.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public LoginUserVO loginByPas(UserLoginRequest loginRequest) {
        String userName = loginRequest.getUserName();
        SysUser user = baseMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUserName, userName));
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

    /**
     * 登录验证
     *
     * @param loginRequest
     * @return
     */
    @Override
    public String login(UserLoginRequest loginRequest) {
        String userName = loginRequest.getUserName();
        String userPassword = loginRequest.getUserPassword();
        String code = loginRequest.getCode();
        String uuid = loginRequest.getUuid();
        // 1. 验证图片验证码的

        // 2. 用户验证
        Authentication authentication;
        try {
            // 该方法会去调用 UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (Exception e) {
            // <2.1> 发生异常，说明验证不通过，记录相应的登录失败日志
            if (e instanceof BadCredentialsException) {
//                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
//                throw new UserPasswordNotMatchException();
            } else {
//                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
//                throw new CustomException(e.getMessage());
            }
        }
        // <2.2> 验证通过，记录相应的登录成功日志
//        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        // <3> 生成 Token
//        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//        return tokenService.createToken(loginUser);
        return null;
    }

    /**
     * 查询指定用户名对应的 SysUser
     *
     * @param userName
     * @return
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return baseMapper.selectUserByUserName(userName);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode("admin");
        System.out.println(password);
    }
}

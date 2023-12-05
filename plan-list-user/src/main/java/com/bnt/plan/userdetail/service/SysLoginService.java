package com.bnt.plan.userdetail.service;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.bnt.plan.common.ErrorCode;
import com.bnt.plan.constant.CommonConstant;
import com.bnt.plan.exception.BusinessException;
import com.bnt.plan.model.dto.user.UserLoginRequest;
import com.bnt.plan.model.entity.SysUser;
import com.bnt.plan.service.RedisService;
import com.bnt.plan.service.SysLogininforService;
import com.bnt.plan.service.SysUserService;
import com.bnt.plan.userdetail.LoginUser;
import com.bnt.plan.utils.MessageUtils;
import com.bnt.plan.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/11/1 14:30 bnt
 * @history
 */
@Slf4j
@Component
public class SysLoginService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysLogininforService logininforService;
    @Autowired
    private SysUserService sysUserService;

    @Value("${public.aesKey}")
    private String aesKey;

    /**
     * 登录验证
     *
     * @return 结果
     */
    public String login(UserLoginRequest loginRequest) {
        AES aes = SecureUtil.aes(aesKey.getBytes());
        String userName = loginRequest.getUserName();
        String userPassword = aes.decryptStr(loginRequest.getUserPassword());
        String code = loginRequest.getCode();
        String uuid = loginRequest.getUuid();

        // 1. 验证图片验证码的
        String captcha = redisService.getCacheObject(CommonConstant.CAPTCHA_CODE_KEY + uuid);
        if (captcha == null) {
            // 图片验证码不存在
            logininforService.recordLogininfor(userName, CommonConstant.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error"));
            throw new BusinessException("验证码已失效");
        }
        if (!code.equalsIgnoreCase(captcha)) {
            // 图片验证码不正确
            logininforService.recordLogininfor(userName, CommonConstant.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new BusinessException("验证码不正确");
        }
        // 2. 用户验证
        SysUser user = sysUserService.selectUserByUserName(userName);
        // 用户不存在时默认注册
        if (user == null) {
            SysUser sysUser = new SysUser();
            sysUser.setUserName(userName);
            sysUser.setPassword(SecurityUtils.encryptPassword(userPassword));
            sysUser.setCreateBy(userName);
            sysUserService.insert(sysUser);
        }
        Authentication authentication = null;
        try {
            // 该方法会去调用 UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (Exception e) {
            // <2.1> 发生异常，说明验证不通过，记录相应的登录失败日志
            if (e instanceof BadCredentialsException) {
                logininforService.recordLogininfor(userName, CommonConstant.LOGIN_FAIL, MessageUtils.message("user.password.not.match"));
                throw new BusinessException(ErrorCode.ERROR_CODE, "用户名或密码错误");
            } else {
                logininforService.recordLogininfor(userName, CommonConstant.LOGIN_FAIL, e.getMessage());
                throw new BusinessException(ErrorCode.ERROR_CODE, e.getMessage());
            }
        }
        // <2.2> 验证通过，记录相应的登录成功日志
        logininforService.recordLogininfor(userName, CommonConstant.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
//        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        // <3> 生成 Token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return tokenService.createToken(loginUser);
    }
}

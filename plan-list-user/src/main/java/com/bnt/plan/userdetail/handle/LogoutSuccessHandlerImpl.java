package com.bnt.plan.userdetail.handle;

import com.alibaba.fastjson.JSON;
import com.bnt.plan.common.BaseResponse;
import com.bnt.plan.constant.CommonConstant;
import com.bnt.plan.service.SysLogininforService;
import com.bnt.plan.userdetail.LoginUser;
import com.bnt.plan.userdetail.service.TokenService;
import com.bnt.plan.utils.MessageUtils;
import com.bnt.plan.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录处理
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/10/24 16:51 bnt
 * @history
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysLogininforService logininforService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 1. 获得当前的LoginUser
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (loginUser != null) {
            String username = loginUser.getUsername();
            // 2. 删除用户缓存信息
            tokenService.delLoginUser(loginUser.getToken());
            // 3. 记录用户退出日志
            logininforService.recordLogininfor(username, CommonConstant.LOGOUT, "退出成功");
        }
        // 4. 响应退出成功
        ServletUtils.renderString(response, JSON.toJSONString(new BaseResponse<>(0, null, MessageUtils.message("user.logout.success"))));
    }
}

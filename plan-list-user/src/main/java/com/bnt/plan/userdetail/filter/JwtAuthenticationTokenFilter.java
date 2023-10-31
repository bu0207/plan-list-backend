package com.bnt.plan.userdetail.filter;

import com.bnt.plan.userdetail.LoginUser;
import com.bnt.plan.userdetail.service.TokenService;
import com.bnt.plan.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 基于 Token 的认证
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/10/24 16:52 bnt
 * @history
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获得当前的 LoginUser
        LoginUser loginUser = tokenService.getLoginUser(request);
        // 如果存在 LoginUser ，并且未认证过
        if (loginUser != null && SecurityUtils.getAuthentication() == null) {
            // <2> 校验 Token 有效性
            tokenService.verifyToken(loginUser);
            // <3> 创建 UsernamePasswordAuthenticationToken 对象，设置到 SecurityContextHolder 中
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // <4> 继续过滤器
        filterChain.doFilter(request, response);
    }
}

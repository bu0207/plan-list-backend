package com.bnt.plan.userdetail.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.bnt.plan.constant.CommonConstant;
import com.bnt.plan.service.RedisService;
import com.bnt.plan.userdetail.LoginUser;
import com.bnt.plan.utils.ServletUtils;
import com.bnt.plan.utils.ip.AddressUtils;
import com.bnt.plan.utils.ip.IpUtils;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/10/30 15:56 bnt
 * @history
 */
@Component
public class TokenService {
    /**
     * 令牌自定义标识
     */

    @Value("${token.header}")
    private String header;

    /**
     * 令牌秘钥
     */
    @Value("${token.secret}")
    private String secret;

    /**
     * 令牌有效期（默认30分钟）
     */
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private RedisService redisService;

    /**
     * 获取user信息
     *
     * @param request
     * @return
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // <1.1> 获取请求携带的令牌
        String token = getToken(request);
        if (StrUtil.isNotEmpty(token)) {
            // <1.2> 解析 JWT 的 Token
            Claims claims = parseToken(token);
            // <1.3> 从 Redis 缓存中，获得对应的 LoginUser
            String uuid = (String) claims.get(CommonConstant.LOGIN_USER_KEY);
            String tokenKey = getTokenKey(uuid);
            return redisService.getCacheObject(tokenKey);
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser) {
        if (loginUser != null && StrUtil.isNotEmpty(loginUser.getToken())) {
            String tokenKey = getTokenKey(loginUser.getToken());
            redisService.setCacheObject(tokenKey, loginUser);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StrUtil.isNotEmpty(token)) {
            String tokenKey = getTokenKey(token);
            redisService.del(tokenKey);
        }
    }

    /**
     * 创建令牌
     * 因为 LoginUser.token 添加到 claims 中，最终生成了 JWT 的 Token 。
     * 所以，后续我们可以通过解码该 JWT 的 Token ，从而获得 claims ，最终获得到对应的 LoginUser.token 。
     * 在 JWT 的 Token 中，使用 LoginUser.token 而不是 userId 的好处，可以带来更好的安全性，
     * 避免万一 secret 秘钥泄露之后，黑客可以顺序生成 userId 从而生成对应的 JWT 的 Token ，后续直接可以操作该用户的数据。
     * 不过，这么做之后就不是纯粹的 JWT ，解析出来的 LoginUser.token 需要查询对应的 LoginUser 的 Redis 缓存。
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser) {
        // <1> 设置 LoginUser 的用户唯一标识。注意，这里虽然变量名叫 token ，其实不是身份认证的 Token
        String token = IdUtil.fastUUID();
        loginUser.setToken(token);
        // <2> 设置用户终端相关的信息，包括 IP、城市、浏览器、操作系统
        setUserAgent(loginUser);
        // <3> 记录缓存
        refreshToken(loginUser);
        // <4> 生成 JWT 的 Token
        Map<String, Object> claims = new HashMap<>();
        claims.put(CommonConstant.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    public void setUserAgent(LoginUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 验证令牌有效期，相差不足 20 分钟，自动刷新缓存
     *
     * @param loginUser 用户
     */
    public void verifyToken(LoginUser loginUser) {
        Long expireTime = loginUser.getExpireTime();
        long l = System.currentTimeMillis();
        if (expireTime - l < MILLIS_MINUTE_TEN) {
            String token = loginUser.getToken();
            loginUser.setToken(token);
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据 uuid 将 loginUser 缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisService.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 获取请求token
     *
     * @return
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StrUtil.isNotEmpty(token) && token.startsWith(CommonConstant.TOKEN_PREFIX)) {
            token = token.replace(CommonConstant.TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid) {
        return CommonConstant.LOGIN_TOKEN_KEY + uuid;
    }
}

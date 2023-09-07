package com.bnt.user.utils;

import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * JWTProvider需要至少提供两个方法，一个用来创建我们的token，另一个根据token获取Authentication。
 * provider需要保证Key密钥是唯一的，使用init()构建，否则会抛出异常。
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/9/5 8:59 bnt
 * @history
 */
@Component
@Slf4j
public class JWTProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息生成token
     *
     * @return
     */
    public String generateToken(String userName) {
        Map<String, Object> claims = new java.util.HashMap<>();
        claims.put("CLAIM_KEY_USERNAME", userName);
        claims.put("CLAIM_KEY_CREATED", new Date());
        return generateToken(claims);
    }

    /**
     * 从token中获取登录用户名
     *
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        String userName;
        try {
            Claims claims = getClaimsFormToken(token);
//            userName = claims.getSubject();
            userName = claims.get("CLAIM_KEY_USERNAME").toString();
        } catch (Exception e) {
            userName = null;
        }
        return userName;
    }

    /**
     * 验证token是否有效
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token, String userName) {
        String username = getUserNameFromToken(token);
        return username.equals(userName) && !isTokenExpired(token);
    }

    /**
     * 判断token是否可以被刷新
     *
     * @param token
     * @return
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFormToken(token);
        claims.put("CLAIM_KEY_CREATED", new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否失效
     *
     * @param token
     * @return
     */
    public boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     *
     * @param token
     * @return
     */
    public Date getExpiredDateFromToken(String token) {
        return getClaimsFormToken(token).getExpiration();
    }

    /**
     * 从token中获取荷载
     *
     * @param token
     * @return
     */
    private Claims getClaimsFormToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 根据荷载生成 JWT TOKEN
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject(claims.get("CLAIM_KEY_USERNAME").toString())
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 生成token失效时间
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    public static void main(String[] args) {
        Date date = new Date(System.currentTimeMillis() + 604800 * 1000);
        String s = DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
        System.out.println(s);
    }
}

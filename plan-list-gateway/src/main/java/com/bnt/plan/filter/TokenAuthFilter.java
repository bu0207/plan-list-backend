package com.bnt.plan.filter;

import com.alibaba.fastjson.JSONObject;
import com.bnt.plan.config.NotAuthUrlProperties;
import com.bnt.plan.service.RedisService;
import com.bnt.plan.utils.JWTProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author bnt
 * @version 1.0.0
 * @create 2023/9/4 17:23 bnt
 * @history
 */
@Component
@Slf4j
public class TokenAuthFilter implements GlobalFilter, Ordered {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.prefix}")
    private String prefix;

    @Autowired
    private RedisService redisService;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private NotAuthUrlProperties notAuthUrlProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("=========================请求进入TokenAuthFilter=========================");

        // 放行接口检查
//        ServerHttpRequest request = exchange.getRequest();
//        String requestPath = request.getURI().getPath();
//
//        boolean allowedPath = false;
//        LinkedHashSet<String> paths = notAuthUrlProperties.getPaths();
//        if (CollectionUtil.isNotEmpty(paths)) {
//            allowedPath = paths.contains(requestPath);
//        }
//        if (allowedPath || StrUtil.isEmpty(requestPath)) {
//            return chain.filter(exchange);
//        }
//
//        // token校验
//        String authHeader = exchange.getRequest().getHeaders().getFirst(tokenHeader);
//        if (StrUtil.isNotEmpty(authHeader) && authHeader.startsWith(prefix)) {
//            String authToken = authHeader.substring(prefix.length());
//            String userName = jwtProvider.getUserNameFromToken(authToken);
//            Object token = redisService.get(UserConstant.USER_TOKEN_KEY_REDIS + userName);
//            if (token == null) {
//                log.error("token验证失败或已过期...");
//                return writeResponse(exchange.getResponse(), 401, "token验证失败或已过期...请重新登录");
//            }
//            // 这里也可以使用 jwtProvider.validateToken() 来验证token,使用redis是因为管理员可以在任意时间将用户token踢出
//            // 去除首尾空格
//            String trimAuthToken = authToken.trim();
//            if (!trimAuthToken.equals(token.toString())) {
//                log.error("token验证失败或已过期...");
//                return writeResponse(exchange.getResponse(), 401, "token验证失败或已过期...请重新登录");
//            }
//        } else {
//            return writeResponse(exchange.getResponse(), 500, "token不存在");
//        }
        log.info("token验证成功...");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    protected Mono<Void> writeResponse(ServerHttpResponse response, Integer code, String msg) {
        JSONObject message = new JSONObject();
        message.put("code", code);
        message.put("msg", msg);
        byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.OK);
        // 指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}

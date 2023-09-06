package com.bnt.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
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
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("=========================请求进入TokenAuthFilter=========================");
        String token = exchange.getRequest().getHeaders().getFirst("token");
        if (!"token123".equals(token)) {
            log.error("token验证失败...");
            return writeResponse(exchange.getResponse(), 401, "token验证失败");
        }
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

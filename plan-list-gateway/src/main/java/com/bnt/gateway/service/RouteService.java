package com.bnt.gateway.service;

import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;

/**
 * 路由配置服务
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/9/1 15:13 bnt
 * @history
 */
public interface RouteService {
    /**
     * 更新路由配置
     *
     * @param routeDefinition
     */
    void update(RouteDefinition routeDefinition);

    /**
     * 添加路由配置
     *
     * @param routeDefinition
     */
    void add(RouteDefinition routeDefinition);

    void delete(RouteDefinition routeDefinition);

    String updateList(List<RouteDefinition> definitions);
}

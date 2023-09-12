package com.bnt.plan.service.impl;

import com.bnt.plan.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 路由配置服务实现类
 * RouteDefinitionWriter 提供了对路由的增加删除等操作
 * ApplicationEventPublisher： 是ApplicationContext的父接口之一，他的功能就是发布事件，也就是把某个事件告诉所有与这个事件相关的监听器
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/9/1 15:13 bnt
 * @history
 */
@Service
@Slf4j
public class RouteServiceImpl implements RouteService, ApplicationEventPublisherAware {

    /**
     * 提供了对路由的增加删除等操作
     */
    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    /**
     * 事件发布者
     */
    private ApplicationEventPublisher publisher;

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * 更新路由配置
     *
     * @param routeDefinition
     */
    @Override
    public void update(RouteDefinition routeDefinition) {
        log.info("更新路由配置项：{}", routeDefinition);
        this.routeDefinitionWriter.delete(Mono.just(routeDefinition.getId())).subscribe();
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 添加路由配置
     *
     * @param routeDefinition
     */
    @Override
    public void add(RouteDefinition routeDefinition) {
        log.info("新增路由配置项：{}", routeDefinition);
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    @Override
    public void delete(RouteDefinition routeDefinition) {
        log.info("删除路由配置项：{}", routeDefinition);
        this.routeDefinitionWriter.delete(Mono.just(routeDefinition.getId())).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }


    /**
     * 删除路由
     *
     * @param id
     * @return
     */
    public String delete(String id) {
        try {
            log.info("gateway delete route id {}", id);
            this.routeDefinitionWriter.delete(Mono.just(id));
            this.publisher.publishEvent(new RefreshRoutesEvent(this));

            return "delete success";
        } catch (Exception e) {
            return "delete fail";
        }
    }

    /**
     * 更新路由
     *
     * @param definitions
     * @return
     */
    public String updateList(List<RouteDefinition> definitions) {
        log.info("gateway update route {}", definitions);
        // 删除缓存routerDefinition
        List<RouteDefinition> routeDefinitionsExits = routeDefinitionLocator.getRouteDefinitions().buffer().blockFirst();
        if (!CollectionUtils.isEmpty(routeDefinitionsExits)) {
            routeDefinitionsExits.forEach(routeDefinition -> {
                log.info("delete routeDefinition:{}", routeDefinition);
                delete(routeDefinition.getId());
            });
        }
        definitions.forEach(definition -> {
            updateById(definition);
        });
        return "success";
    }

    /**
     * 更新路由
     *
     * @param definition
     * @return
     */
    public String updateById(RouteDefinition definition) {
        try {
            log.info("gateway update route {}", definition);
            this.routeDefinitionWriter.delete(Mono.just(definition.getId())).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
        } catch (Exception e) {
            return "update fail,not find route  routeId: " + definition.getId();
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            return "update route fail";
        }
    }
}

package com.bnt.plan.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.bnt.plan.service.RouteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * 动态路由实现
 * 删除路由好像不生效
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/9/1 15:40 bnt
 * @history
 */
@Component
@Slf4j
@RefreshScope
public class GatewayRouteInitConfig {
    @Autowired
    private GatewayRouteConfigProperties configProperties;

    @Autowired
    private NacosConfigProperties nacosConfigProperties;

    @Autowired
    private RouteService routeService;

    /**
     * nacos 配置服务
     */
    @Autowired
    private ConfigService configService;

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        log.info("gateway route init...");
        try {
            if (configService == null) {
                log.warn("initConfigService fail");
                return;
            }
            String configInfo = configService.getConfig(configProperties.getDataId(), configProperties.getGroup(), nacosConfigProperties.getTimeout());
            log.info("获取网关当前配置:\r\n{}", configInfo);
            List<RouteDefinition> definitionList = JSON.parseArray(configInfo, RouteDefinition.class);
            for (RouteDefinition definition : definitionList) {
                log.info("init route : {}", definition.toString());
                routeService.add(definition);
            }
        } catch (Exception e) {
            log.error("初始化网关路由时发生错误", e);
        }
        dynamicRouteByNacosListener(configProperties.getDataId(), configProperties.getGroup());
    }

    /**
     * 监听Nacos下发的动态路由配置
     *
     * @param dataId
     * @param group
     */
    public void dynamicRouteByNacosListener(String dataId, String group) {
        try {
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("进行网关更新:\n\r{}", configInfo);
                    List<RouteDefinition> definitionList = JSON.parseArray(configInfo, RouteDefinition.class);
                    log.info("update route : {}", definitionList.toString());
                    routeService.updateList(definitionList);
                }

                @Override
                public Executor getExecutor() {
                    log.info("getExecutor\n\r");
                    return null;
                }
            });
        } catch (NacosException e) {
            log.error("从nacos接收动态路由配置出错!!!", e);
        }
    }


    public void init2() {
        log.info("开始网关动态路由初始化...");
        try {
            // getConfigAndSignListener()方法 发起长轮询和对dataId数据变更注册监听的操作
            // configService.getConfig() 只是发送普通的HTTP请求
            String initConfigInfo = configService.getConfigAndSignListener(configProperties.getDataId(), configProperties.getGroup(), nacosConfigProperties.getTimeout(), new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    if (StrUtil.isNotEmpty(configInfo)) {
                        log.info("接收到网关路由更新配置：\r\n{}", configInfo);
                        List<RouteDefinition> routeDefinitions = null;
                        try {
                            routeDefinitions = objectMapper.readValue(configInfo, new TypeReference<List<RouteDefinition>>() {
                            });
                        } catch (JsonProcessingException e) {
                            log.error("解析路由配置出错，" + e.getMessage(), e);
                        }
                        // 解决删除路由不生效问题
                        List<RouteDefinition> routeDefinitionsExits = routeDefinitionLocator.getRouteDefinitions().buffer().blockFirst();
                        if (!CollectionUtils.isEmpty(routeDefinitionsExits)) {
                            routeDefinitionsExits.forEach(routeDefinition -> {
                                routeService.delete(routeDefinition);
                            });
                        }
                        for (RouteDefinition definition : Objects.requireNonNull(routeDefinitions)) {
                            routeService.update(definition);
                        }
                    } else {
                        log.warn("当前网关无动态路由相关配置");
                    }
                }
            });
            log.info("获取网关当前动态路由配置:\r\n{}", initConfigInfo);
            if (StrUtil.isNotEmpty(initConfigInfo)) {
                List<RouteDefinition> routeDefinitions = objectMapper.readValue(initConfigInfo, new TypeReference<List<RouteDefinition>>() {
                });
                for (RouteDefinition definition : routeDefinitions) {
                    routeService.add(definition);
                }
            } else {
                log.warn("当前网关无动态路由相关配置");
            }
            log.info("结束网关动态路由初始化...");
        } catch (Exception e) {
            log.error("初始化网关路由时发生错误", e);
        }
    }
}

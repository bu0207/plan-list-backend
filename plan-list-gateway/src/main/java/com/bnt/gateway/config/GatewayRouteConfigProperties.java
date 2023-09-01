package com.bnt.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 动态路由配置类
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/9/1 15:33 bnt
 * @history
 */
@ConfigurationProperties(prefix = "gateway.routes.config")
@Component
@Data
public class GatewayRouteConfigProperties {
    private String dataId;

    private String group;

    private String namespace;
}

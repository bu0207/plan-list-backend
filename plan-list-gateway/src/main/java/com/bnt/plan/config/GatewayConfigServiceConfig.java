package com.bnt.plan.config;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 将configService交由spring管理
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/9/1 15:36 bnt
 * @history
 */
@Configuration
public class GatewayConfigServiceConfig {
    @Autowired
    private GatewayRouteConfigProperties configProperties;

    @Autowired
    private NacosConfigProperties nacosConfigProperties;

    @Bean
    public ConfigService configService() throws NacosException {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, nacosConfigProperties.getServerAddr());
        properties.setProperty(PropertyKeyConst.NAMESPACE, configProperties.getNamespace());
        return NacosFactory.createConfigService(properties);
    }
}

package com.bnt.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

/**
 * 跳过登录接口
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/9/8 10:23 bnt
 * @history
 */
@Data
@Component
@ConfigurationProperties("allowed.gateway")
public class NotAuthUrlProperties {
    private LinkedHashSet<String> paths;
}

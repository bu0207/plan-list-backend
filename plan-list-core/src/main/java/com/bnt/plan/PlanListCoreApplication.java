package com.bnt.plan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 清单核心启动类
 * EnableDiscoveryClient 开启服务注册发现
 * EnableFeignClients开启支持feign的远程调用
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/8/30 14:58 bnt
 * @history
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PlanListCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlanListCoreApplication.class);
    }
}

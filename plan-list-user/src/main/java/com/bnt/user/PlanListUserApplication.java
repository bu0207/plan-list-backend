package com.bnt.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 清单-用户 应用
 * EnableDiscoveryClient 开启服务注册发现功能
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/8/30 10:30 bnt
 * @history
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.bnt.user.mapper")
public class PlanListUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlanListUserApplication.class, args);
    }
}

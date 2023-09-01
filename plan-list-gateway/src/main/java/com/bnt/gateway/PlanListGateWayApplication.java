package com.bnt.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 网关启动类
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/9/1 14:07 bnt
 * @history
 */
@SpringBootApplication
@EnableFeignClients
public class PlanListGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlanListGateWayApplication.class, args);
    }
}

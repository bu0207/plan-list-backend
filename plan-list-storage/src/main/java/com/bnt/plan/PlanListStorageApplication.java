package com.bnt.plan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 清单仓储服务
 * 开启数据源代理@EnableAutoDataSourceProxy
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/9/11 11:14 bnt
 * @history
 */
@SpringBootApplication
public class PlanListStorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlanListStorageApplication.class, args);
    }
}

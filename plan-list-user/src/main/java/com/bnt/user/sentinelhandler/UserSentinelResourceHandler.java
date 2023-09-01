package com.bnt.user.sentinelhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Component;

/**
 * 熔断处理
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/9/1 9:59 bnt
 * @history
 */
@Component
public class UserSentinelResourceHandler {
    public static String sentinelAResource(Throwable throwable) {
        System.out.println("触发熔断，服务不可用");
        return "触发熔断，服务不可用";
    }

    public static String hotspotResource(String userId, String shopId, BlockException blockException) {
        System.out.println("您被认为恶意访问，触发热点限流");
        return "您被认为恶意访问，触发热点限流";
    }
}

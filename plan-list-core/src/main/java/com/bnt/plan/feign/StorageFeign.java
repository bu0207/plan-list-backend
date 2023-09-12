package com.bnt.plan.feign;

import com.bnt.plan.common.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * http服务调用
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/8/30 17:30 bnt
 * @history
 */
@FeignClient(name = "plan-list-storage")
@Component
public interface StorageFeign {

    /**
     * 扣减库存
     *
     * @param commodityCode
     * @param count
     * @return
     */
    @GetMapping("storage/storagetbl/deduct")
    BaseResponse deduct(@RequestParam String commodityCode, @RequestParam Integer count);
}

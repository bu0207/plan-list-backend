package com.bnt.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bnt.core.model.entity.OrderTbl;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bnt
 * @since 2023-09-11
 */
public interface OrderTblService extends IService<OrderTbl> {
    /**
     * 下单接口
     * @param userId 用户id
     * @param commodityCode 商品代码
     * @return
     */
    String createOrder(String userId, String commodityCode);
}

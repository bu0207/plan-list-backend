package com.bnt.plan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bnt.plan.model.entity.StorageTbl;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bnt
 * @since 2023-09-11
 */
public interface StorageTblService extends IService<StorageTbl> {
    /**
     * 扣除存储数量
     */
    void deduct(String commodityCode, int count);
}

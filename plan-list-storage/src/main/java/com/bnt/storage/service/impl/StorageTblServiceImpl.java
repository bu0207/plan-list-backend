package com.bnt.storage.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnt.common.exception.BusinessException;
import com.bnt.storage.mapper.StorageTblMapper;
import com.bnt.storage.model.entity.StorageTbl;
import com.bnt.storage.service.StorageTblService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author bnt
 * @since 2023-09-11
 */
@Service
@Slf4j
public class StorageTblServiceImpl extends ServiceImpl<StorageTblMapper, StorageTbl> implements StorageTblService {

    /**
     * 扣除存储数量
     *
     * @param commodityCode
     * @param count
     */
    @Override
    @Transactional
    public void deduct(String commodityCode, int count) {
        System.out.println("事务id---------------------->" + RootContext.getXID());
        StorageTbl storageTbl = baseMapper.selectOne(new QueryWrapper<StorageTbl>().lambda()
                .eq(StorageTbl::getCommodityCode, commodityCode));
        if (storageTbl == null) {
            throw new BusinessException("storageTbl is null");
        }

        // 模拟异常
        if (commodityCode.equals("product-2")) {
            throw new RuntimeException("异常:模拟业务异常:stock branch exception");
        }

        // 这里先不考虑超卖的情况
        storageTbl.setCount(storageTbl.getCount() - count);
        // 使用jpa 存在就更新
        baseMapper.updateById(storageTbl);
    }
}

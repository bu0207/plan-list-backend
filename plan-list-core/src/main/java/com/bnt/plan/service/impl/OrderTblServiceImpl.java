package com.bnt.plan.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnt.plan.feign.StorageFeign;
import com.bnt.plan.mapper.OrderTblMapper;
import com.bnt.plan.model.entity.OrderTbl;
import com.bnt.plan.service.OrderTblService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
public class OrderTblServiceImpl extends ServiceImpl<OrderTblMapper, OrderTbl> implements OrderTblService {

    @Resource
    private StorageFeign storageFeign;

    /**
     * 下单接口
     *
     * @param userId        用户id
     * @param commodityCode 商品代码
     * @return
     */
    @Override
    @GlobalTransactional
        public String createOrder(String userId, String commodityCode) {

        System.out.println("事务id---------------------->" + RootContext.getXID());
        // 创建订单
        OrderTbl orderTbl = new OrderTbl();
        orderTbl.setUserId(userId);
        orderTbl.setCommodityCode(commodityCode);
        orderTbl.setCount(1); // 假设为1件
        orderTbl.setMoney(10); // 假设为十元

        // 保存订单
        baseMapper.insert(orderTbl);

        // 扣减库存
        storageFeign.deduct(commodityCode, orderTbl.getCount());


        return "success";


    }

}

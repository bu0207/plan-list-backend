//package com.bnt.core.feign;
//
//import com.bnt.common.common.BaseResponse;
//import com.bnt.common.common.ResultUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestParam;
//
///**
// * OrderFeign容错处理
// *
// * @author bnt
// * @version 1.0.0
// * @create 2023/8/31 15:19 bnt
// * @history
// */
//@Component
//public class StorageFeignHandler implements StorageFeign {
//    @Override
//    public BaseResponse deduct(@RequestParam String commodityCode, @RequestParam Integer count) {
//        return ResultUtils.error(500, "当前人数过多,休息一会再试");
//    }
//}

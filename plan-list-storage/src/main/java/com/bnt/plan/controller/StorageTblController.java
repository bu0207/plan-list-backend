package com.bnt.plan.controller;


import com.bnt.plan.common.BaseResponse;
import com.bnt.plan.common.ResultUtils;
import com.bnt.plan.service.StorageTblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bnt
 * @since 2023-09-11
 */
@RestController
@RequestMapping("/storagetbl")
public class StorageTblController {

    @Autowired
    private StorageTblService storageTblService;

    @GetMapping("/deduct")
    public BaseResponse deduct(String commodityCode, int count){
        storageTblService.deduct(commodityCode, count);
        return ResultUtils.success();
    }
}

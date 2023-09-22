package com.bnt.plan.controller;


import com.bnt.plan.common.BaseResponse;
import com.bnt.plan.common.ResultUtils;
import com.bnt.plan.model.vo.ListInfoReqVO;
import com.bnt.plan.service.ListInfoService;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 清单表 前端控制器
 * </p>
 *
 * @author bnt
 * @since 2023-09-22
 */
@ApiModel("清单接口")
@RestController
@RequestMapping("/list")
public class ListInfoController {

    @Autowired
    private ListInfoService listInfoService;

    @PostMapping("add")
    public BaseResponse<String> add(@RequestBody @Valid ListInfoReqVO listInfoReqVO) {
        return ResultUtils.success(listInfoService.add(listInfoReqVO));
    }
}

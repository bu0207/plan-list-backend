package com.bnt.plan.controller;


import com.bnt.plan.common.BaseResponse;
import com.bnt.plan.common.ResultUtils;
import com.bnt.plan.model.vo.listinfo.ListAllResVO;
import com.bnt.plan.model.vo.listinfo.ListInfoReqVO;
import com.bnt.plan.model.vo.listinfo.ListUpdateReqVO;
import com.bnt.plan.service.ListInfoService;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    /**
     * 清单新增
     *
     * @param listInfoReqVO
     * @return
     */
    @PostMapping("add")
    public BaseResponse<String> add(@RequestBody @Valid ListInfoReqVO listInfoReqVO) {
        return ResultUtils.success(listInfoService.add(listInfoReqVO));
    }

    /**
     * 清单修改
     *
     * @param listUpdateReqVO
     * @return
     */
    @PostMapping("update")
    public BaseResponse<Integer> update(@RequestBody @Valid ListUpdateReqVO listUpdateReqVO) {
        return ResultUtils.success(listInfoService.update(listUpdateReqVO));
    }

    /**
     * 清单删除
     *
     * @return
     */
    @GetMapping("delete")
    public BaseResponse<Integer> delete(String id) {
        return ResultUtils.success(listInfoService.delete(id));
    }

    /**
     * 获取清单列表
     *
     * @return
     */
    @PostMapping("allList")
    public BaseResponse<List<ListAllResVO>> allList() {
        return ResultUtils.success(listInfoService.allList());
    }
}

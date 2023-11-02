package com.bnt.plan.controller;


import com.bnt.plan.common.BaseResponse;
import com.bnt.plan.common.ResultUtils;
import com.bnt.plan.model.entity.SysUser;
import com.bnt.plan.service.SysUserService;
import com.bnt.plan.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author bnt
 * @since 2023-10-23
 */
@Api(tags = "用户")
@Slf4j
@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "新增用户", httpMethod = "POST")
    @PostMapping("/add")
    public BaseResponse add(@Valid @RequestBody SysUser sysUser) {
        if (sysUserService.checkUserNameUnique(sysUser.getUserName())) {
            return ResultUtils.error("新增用户'" + sysUser.getUserName() + "'失败，登录账号已存在");
        }
        if (sysUserService.checkPhoneUnique(sysUser)) {
            return ResultUtils.error("新增用户'" + sysUser.getUserName() + "'失败,手机号码已存在");
        }
        if (sysUserService.checkEmailUnique(sysUser)) {
            return ResultUtils.error("新增用户'" + sysUser.getUserName() + "'失败,邮箱账号已存在");
        }
        sysUser.setPassword(SecurityUtils.encryptPassword(sysUser.getPassword()));
        sysUser.setCreateBy(SecurityUtils.getUsername());
        return ResultUtils.success(sysUserService.insert(sysUser));
    }

    @ApiOperation(value = "重置密码", httpMethod = "POST")
    @PostMapping("/resetPwd")
    public BaseResponse resetPwd(@RequestBody SysUser sysUser) {
        sysUserService.checkUserAllowed(sysUser);
        sysUser.setPassword(SecurityUtils.encryptPassword(sysUser.getPassword()));
        sysUser.setUpdateBy(SecurityUtils.getUsername());
        return ResultUtils.success(sysUserService.lambdaUpdate()
                .set(SysUser::getPassword, sysUser.getPassword())
                .eq(SysUser::getUserId, sysUser.getUserId())
                .update());
    }
}

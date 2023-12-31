package com.bnt.plan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bnt.plan.model.entity.SysRole;

import java.util.Set;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author bnt
 * @since 2023-10-23
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 获取角色数据权限
     */
    Set<String> selectRolePermissionByUserId(Long userId);
}

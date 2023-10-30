package com.bnt.plan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bnt.plan.model.entity.SysMenu;

import java.util.Set;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author bnt
 * @since 2023-10-23
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取用户权限
     *
     * @param userId
     * @return
     */
    Set<String> selectMenuPermsByUserId(Long userId);
}

package com.bnt.plan.userdetail.service;

import com.bnt.plan.model.entity.SysUser;
import com.bnt.plan.service.SysMenuService;
import com.bnt.plan.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户权限处理
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/10/30 14:50 bnt
 * @history
 */
@Component
public class SysPermissionService {

    @Autowired
    private SysMenuService menuService;
    @Autowired
    private SysRoleService roleService;

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUser user) {
        Set<String> roles = new HashSet<>();
        if (user.isAdmin()) {
            // 所有模块
            roles.add("*:*:*");
        } else {
            roles.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
        }
        return roles;
    }

    /**
     * 获取角色数据权限
     */
    public Set<String> getRolePermission(SysUser user) {
        Set<String> roles = new HashSet<>();
        if (user.isAdmin()) {
            roles.add("admin");
        } else {
            roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return roles;
    }
}

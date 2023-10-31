package com.bnt.plan.userdetail.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.bnt.plan.model.entity.SysRole;
import com.bnt.plan.userdetail.LoginUser;
import com.bnt.plan.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 自定义权限实现，ss取自SpringSecurity首字母
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/10/31 10:38 bnt
 * @history
 */
@Service("ss")
public class PermissionService {
    /**
     * 所有权限标识
     */
    private static final String ALL_PERMISSION = "*:*:*";

    /**
     * 管理员角色权限标识
     */
    private static final String SUPER_ADMIN = "admin";

    private static final String ROLE_DELIMETER = ",";

    private static final String PERMISSION_DELIMETER = ",";

    @Autowired
    private TokenService tokenService;

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission) {
        // 如果未设置需要的权限，强制不具备
        if (StrUtil.isEmpty(permission)) {
            return false;
        }
        // 获得当前的LoginUser
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 如果不存在或者不具备任何权限，说明权限验证不通过
        if (loginUser == null || CollectionUtil.isEmpty(loginUser.getPermissions())) {
            return false;
        }
        // 判断是否包含指定的任何一个权限
        Set<String> authorities = loginUser.getPermissions();
        for (String pe : permission.split(PERMISSION_DELIMETER)) {
            if (StrUtil.isNotEmpty(pe) && hasPermission(authorities, pe)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否拥有某个角色
     *
     * @param role 角色字符串
     * @return 用户是否具备某角色
     */
    public boolean hasRole(String role) {
        // 如果未设置需要的角色，强制不具备
        if (StrUtil.isEmpty(role)) {
            return false;
        }
        // 获得当前的LoginUser
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 如果不存在或者没有任何角色，说明权限验证不通过
        if (loginUser == null || CollectionUtil.isEmpty(loginUser.getUser().getRoles())) {
            return false;
        }
        List<SysRole> roleList = loginUser.getUser().getRoles();
        if (hasRole(roleList, role)) {
            return true;
        }
        return false;
    }

    /**
     * 验证用户是否不具备某角色，与 isRole逻辑相反。
     *
     * @param role 角色名称
     * @return 用户是否不具备某角色
     */
    public boolean lacksRole(String role) {
        return !hasRole(role);
    }

    /**
     * 验证用户是否具有以下任意一个角色
     *
     * @param roles 以 ROLE_NAMES_DELIMETER 为分隔符的角色列表
     * @return 用户是否具有以下任意一个角色
     */
    public boolean hasAnyRoles(String roles) {
        if (StrUtil.isEmpty(roles)) {
            return false;
        }
        // 获得当前的LoginUser
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 如果不存在或者没有任何角色，说明权限验证不通过
        if (loginUser == null || CollectionUtil.isEmpty(loginUser.getUser().getRoles())) {
            return false;
        }
        List<SysRole> roleList = loginUser.getUser().getRoles();
        for (String role : roles.split(ROLE_DELIMETER)) {
            if (hasRole(roleList, role)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasRole(List<SysRole> roleList, String role) {
        for (SysRole sysRole : roleList) {
            String roleKey = sysRole.getRoleKey();
            if (SUPER_ADMIN.contains(roleKey)
                    || roleKey.contains(role.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否包含权限
     *
     * @param permissions 权限列表
     * @param permission  权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasPermission(Set<String> permissions, String permission) {
        return permissions.contains(ALL_PERMISSION) || permissions.contains(permission.trim());
    }
}

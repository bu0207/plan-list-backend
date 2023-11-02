package com.bnt.plan.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnt.plan.mapper.SysRoleMapper;
import com.bnt.plan.model.entity.SysRole;
import com.bnt.plan.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author bnt
 * @since 2023-10-23
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    /**
     * 获取角色数据权限
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> sysRoles = baseMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole sysRole : sysRoles) {
            if (StrUtil.isNotEmpty(sysRole.getRoleKey())) {
                permsSet.addAll(Arrays.asList(sysRole.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }
}

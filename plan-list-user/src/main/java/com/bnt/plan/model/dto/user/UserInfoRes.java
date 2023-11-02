package com.bnt.plan.model.dto.user;

import com.bnt.plan.model.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * 用户信息
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/11/2 15:36 bnt
 * @history
 */
@Data
@AllArgsConstructor
public class UserInfoRes {
    private SysUser user;

    private Set<String> rolePermission;

    private Set<String> menuPermission;
}

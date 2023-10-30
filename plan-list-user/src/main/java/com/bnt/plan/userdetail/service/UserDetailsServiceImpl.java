package com.bnt.plan.userdetail.service;

import com.bnt.plan.enumlist.StatusEnum;
import com.bnt.plan.exception.BusinessException;
import com.bnt.plan.model.entity.SysUser;
import com.bnt.plan.service.SysUserService;
import com.bnt.plan.userdetail.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 自定义用户认证逻辑
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/10/24 16:32 bnt
 * @history
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // <1> 查询指定用户名对应的 SysUser
        SysUser user = sysUserService.selectUserByUserName(username);
        log.info("登录用户{}，不存在", username);
        if (user == null) {
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        if (StatusEnum.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new BusinessException("对不起，您的账号：" + username + " 已被删除");
        }
        if (StatusEnum.DISABLED.getCode().equals(user.getStatus())) {
            log.info("登录用户:{} 已被停用.", username);
            throw new BusinessException("对不起，您的账号 " + username + " 已被停用");
        }
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user, permissionService.getMenuPermission(user));
    }
}

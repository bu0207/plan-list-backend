package com.bnt.plan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bnt.plan.model.dto.user.UserLoginRequest;
import com.bnt.plan.model.entity.SysUser;
import com.bnt.plan.model.vo.LoginUserVO;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author bnt
 * @since 2023-09-22
 */
public interface SysUserService extends IService<SysUser> {
    LoginUserVO loginByPas(UserLoginRequest loginRequest);

    /**
     * 查询指定用户名对应的 SysUser
     * @param userName
     * @return
     */
    SysUser selectUserByUserName(String userName);
}

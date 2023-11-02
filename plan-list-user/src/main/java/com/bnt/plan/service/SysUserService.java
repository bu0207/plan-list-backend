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
     *
     * @param userName
     * @return
     */
    SysUser selectUserByUserName(String userName);

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    boolean checkUserNameUnique(String userName);

    /**
     * 校验手机号是否唯一
     *
     * @param sysUser 用户信息
     * @return
     */
    boolean checkPhoneUnique(SysUser sysUser);

    /**
     * 校验email是否唯一
     *
     * @param sysUser
     * @return
     */
    boolean checkEmailUnique(SysUser sysUser);

    /**
     * 新增保存用户信息
     *
     * @param sysUser
     */
    int insert(SysUser sysUser);

    /**
     * 校验用户是否允许操作
     *
     * @param sysUser
     */
    void checkUserAllowed(SysUser sysUser);
}

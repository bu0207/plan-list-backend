package com.bnt.plan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnt.plan.constant.UserConstant;
import com.bnt.plan.exception.BusinessException;
import com.bnt.plan.mapper.SysUserMapper;
import com.bnt.plan.model.dto.user.UserLoginRequest;
import com.bnt.plan.model.entity.SysUser;
import com.bnt.plan.model.entity.SysUserRole;
import com.bnt.plan.model.vo.LoginUserVO;
import com.bnt.plan.service.RedisService;
import com.bnt.plan.service.SysUserRoleService;
import com.bnt.plan.service.SysUserService;
import com.bnt.plan.utils.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author bnt
 * @since 2023-09-22
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Value("${jwt.prefix}")
    private String prefix;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private RedisService redisService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public LoginUserVO loginByPas(UserLoginRequest loginRequest) {
        String userName = loginRequest.getUserName();
        SysUser user = baseMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUserName, userName));
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 判断用户名密码是否正确
        if (StrUtil.isEmpty(userName) ||
                !encoder.matches(loginRequest.getUserPassword(), user.getPassword())) {
            throw new BusinessException("用户名或者密码错误");
        }
        // 生成token
        String token = jwtProvider.generateToken(loginRequest.getUserName());

        // 将token存入redis
        redisService.set(UserConstant.USER_TOKEN_KEY_REDIS + userName, token, 604800);

        return LoginUserVO.builder().userName(userName).id(user.getUserId()).token(prefix + " " + token).build();
    }


    /**
     * 查询指定用户名对应的 SysUser
     *
     * @param userName
     * @return
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return baseMapper.selectUserByUserName(userName);
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public boolean checkUserNameUnique(String userName) {
        SysUser user = baseMapper.selectOne(new QueryWrapper<SysUser>()
                .lambda().eq(SysUser::getUserName, userName));
        return user == null;
    }

    /**
     * 校验手机号是否唯一
     *
     * @param sysUser 用户信息
     * @return
     */
    @Override
    public boolean checkPhoneUnique(SysUser sysUser) {
        SysUser user = baseMapper.selectOne(new QueryWrapper<SysUser>()
                .lambda().eq(SysUser::getPhone, sysUser.getPhone()));
        return user == null;
    }

    /**
     * 校验email是否唯一
     *
     * @param sysUser
     * @return
     */
    @Override
    public boolean checkEmailUnique(SysUser sysUser) {
        SysUser user = baseMapper.selectOne(new QueryWrapper<SysUser>()
                .lambda().eq(SysUser::getEmail, sysUser.getEmail()));
        return user == null;
    }

    /**
     * 新增保存用户信息
     *
     * @param sysUser
     */
    @Override
    @Transactional
    public int insert(SysUser sysUser) {
        int row = baseMapper.insert(sysUser);
        insertUserRole(sysUser);
        return row;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param sysUser
     */
    @Override
    public void checkUserAllowed(SysUser sysUser) {
        if (sysUser.getUserId() != null && sysUser.isAdmin()) {
            throw new BusinessException("不允许操作超级管理员用户");
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        Long[] roles = user.getRoleIds();
        if (roles != null && roles.length > 0) {
            List<SysUserRole> list = new ArrayList<>();
            for (Long roleId : roles) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(user.getUserId());
                sysUserRole.setRoleId(roleId);
                list.add(sysUserRole);
            }
            if (CollectionUtil.isNotEmpty(list)) {
                sysUserRoleService.saveBatch(list);
            }
        }
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode("admin");
        System.out.println(password);
    }
}

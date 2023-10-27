package com.bnt.plan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bnt.plan.model.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author bnt
 * @since 2023-10-23
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser selectUserByUserName(String userName);
}

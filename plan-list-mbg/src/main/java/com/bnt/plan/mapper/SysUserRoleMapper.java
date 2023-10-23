package com.bnt.plan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bnt.plan.model.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户和角色关联表 Mapper 接口
 * </p>
 *
 * @author bnt
 * @since 2023-10-23
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}

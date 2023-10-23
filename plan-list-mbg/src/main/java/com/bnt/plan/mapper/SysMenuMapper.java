package com.bnt.plan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bnt.plan.model.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author bnt
 * @since 2023-10-23
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}

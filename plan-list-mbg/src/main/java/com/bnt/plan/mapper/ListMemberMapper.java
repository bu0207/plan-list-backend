package com.bnt.plan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bnt.plan.model.entity.ListMember;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 清单成员表 Mapper 接口
 * </p>
 *
 * @author bnt
 * @since 2023-09-22
 */
@Mapper
public interface ListMemberMapper extends BaseMapper<ListMember> {

}

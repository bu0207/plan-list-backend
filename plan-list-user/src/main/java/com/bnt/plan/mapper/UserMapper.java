package com.bnt.plan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bnt.plan.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bnt
 * @since 2023-09-06
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

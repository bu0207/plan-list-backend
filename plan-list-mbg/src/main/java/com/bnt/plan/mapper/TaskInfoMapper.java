package com.bnt.plan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bnt.plan.model.entity.TaskInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 任务表 Mapper 接口
 * </p>
 *
 * @author bnt
 * @since 2023-09-22
 */
@Mapper
public interface TaskInfoMapper extends BaseMapper<TaskInfo> {

}

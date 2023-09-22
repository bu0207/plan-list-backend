package com.bnt.plan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnt.plan.entity.TaskInfo;
import com.bnt.plan.mapper.TaskInfoMapper;
import com.bnt.plan.service.TaskInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 任务表 服务实现类
 * </p>
 *
 * @author bnt
 * @since 2023-09-22
 */
@Service
public class TaskInfoServiceImpl extends ServiceImpl<TaskInfoMapper, TaskInfo> implements TaskInfoService {

}

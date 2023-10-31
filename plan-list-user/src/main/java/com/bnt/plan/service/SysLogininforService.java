package com.bnt.plan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bnt.plan.model.entity.SysLogininfor;

/**
 * <p>
 * 系统访问记录表 服务类
 * </p>
 *
 * @author bnt
 * @since 2023-10-31
 */
public interface SysLogininforService extends IService<SysLogininfor> {

    /**
     * 记录登陆信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public void recordLogininfor(final String username, final String status, final String message,
                                 final Object... args);
}

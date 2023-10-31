package com.bnt.plan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnt.plan.constant.CommonConstant;
import com.bnt.plan.mapper.SysLogininforMapper;
import com.bnt.plan.model.entity.SysLogininfor;
import com.bnt.plan.service.SysLogininforService;
import com.bnt.plan.utils.ServletUtils;
import com.bnt.plan.utils.ip.AddressUtils;
import com.bnt.plan.utils.ip.IpUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统访问记录表 服务实现类
 * </p>
 *
 * @author bnt
 * @since 2023-10-31
 */
@Service
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforMapper, SysLogininfor> implements SysLogininforService {

    /**
     * 记录登陆信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    @Override
    @Async
    public void recordLogininfor(String username, String status, String message, Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        String address = AddressUtils.getRealAddressByIP(ip);
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setUserName(username);
        logininfor.setIpaddr(ip);
        logininfor.setLoginLocation(address);
        logininfor.setBrowser(browser);
        logininfor.setOs(os);
        logininfor.setMsg(message);
        // 日志状态
        if (CommonConstant.LOGIN_SUCCESS.equals(status) || CommonConstant.LOGOUT.equals(status)) {
            logininfor.setStatus(CommonConstant.SUCCESS);
        } else if (CommonConstant.LOGIN_FAIL.equals(status)) {
            logininfor.setStatus(CommonConstant.FAIL);
        }
        // 插入数据
        baseMapper.insert(logininfor);
    }
}

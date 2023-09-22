package com.bnt.plan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bnt.plan.model.entity.ListInfo;
import com.bnt.plan.model.vo.ListInfoReqVO;

/**
 * <p>
 * 清单表 服务类
 * </p>
 *
 * @author bnt
 * @since 2023-09-22
 */
public interface ListInfoService extends IService<ListInfo> {

    String add(ListInfoReqVO listInfoReqVO);
}

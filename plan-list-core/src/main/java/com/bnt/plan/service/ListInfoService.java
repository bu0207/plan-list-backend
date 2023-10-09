package com.bnt.plan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bnt.plan.model.entity.ListInfo;
import com.bnt.plan.model.vo.listinfo.ListAllResVO;
import com.bnt.plan.model.vo.listinfo.ListInfoReqVO;
import com.bnt.plan.model.vo.listinfo.ListUpdateReqVO;

import java.util.List;

/**
 * <p>
 * 清单表 服务类
 * </p>
 *
 * @author bnt
 * @since 2023-09-22
 */
public interface ListInfoService extends IService<ListInfo> {

    /**
     * 清单新增
     */
    String add(ListInfoReqVO listInfoReqVO);

    /**
     * 清单修改
     */
    Integer update(ListUpdateReqVO listUpdateReqVO);

    /**
     * 清单删除
     *
     * @param id
     * @return
     */
    Integer delete(String id);

    /**
     * 清单列表
     * @return
     */
    List<ListAllResVO> allList();
}

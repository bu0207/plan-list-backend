package com.bnt.plan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnt.plan.mapper.ListInfoMapper;
import com.bnt.plan.model.entity.ListInfo;
import com.bnt.plan.model.vo.ListInfoReqVO;
import com.bnt.plan.service.ListInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 清单表 服务实现类
 * </p>
 *
 * @author bnt
 * @since 2023-09-22
 */
@Service
public class ListInfoServiceImpl extends ServiceImpl<ListInfoMapper, ListInfo> implements ListInfoService {

    @Override
    public String add(ListInfoReqVO listInfoReqVO) {
        ListInfo listInfo = new ListInfo();
        listInfo.setListName(listInfoReqVO.getListName());
        listInfo.setListColor(listInfoReqVO.getListColor());
        listInfo.setListType(listInfoReqVO.getListType());
        baseMapper.insert(listInfo);
        return listInfo.getId();
    }
}

package com.bnt.plan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnt.plan.exception.BusinessException;
import com.bnt.plan.mapper.ListInfoMapper;
import com.bnt.plan.model.entity.ListInfo;
import com.bnt.plan.model.vo.listinfo.ListAllResVO;
import com.bnt.plan.model.vo.listinfo.ListInfoReqVO;
import com.bnt.plan.model.vo.listinfo.ListUpdateReqVO;
import com.bnt.plan.service.ListInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 清单新增
     */
    @Override
    public String add(ListInfoReqVO listInfoReqVO) {
        ListInfo listInfo = new ListInfo();
        listInfo.setListName(listInfoReqVO.getListName());
        listInfo.setListColor(listInfoReqVO.getListColor());
        listInfo.setListType(listInfoReqVO.getListType());
        baseMapper.insert(listInfo);
        return listInfo.getId();
    }

    /**
     * 清单修改
     *
     * @param listUpdateReqVO
     */
    @Override
    public Integer update(ListUpdateReqVO listUpdateReqVO) {
        String id = listUpdateReqVO.getId();
        ListInfo listInfo = baseMapper.selectById(id);
        if (listInfo == null) {
            throw new BusinessException("清单不存在");
        }
        listInfo.setListName(listUpdateReqVO.getListName());
        listInfo.setListColor(listUpdateReqVO.getListColor());
        listInfo.setListType(listUpdateReqVO.getListType());
        return baseMapper.updateById(listInfo);
    }

    /**
     * 清单删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer delete(String id) {
        ListInfo listInfo = baseMapper.selectById(id);
        if (listInfo == null) {
            throw new BusinessException("清单不存在");
        }
        return baseMapper.deleteById(id);
    }

    /**
     * 清单列表
     *
     * @return
     */
    @Override
    public List<ListAllResVO> allList() {
        ArrayList<ListAllResVO> listAllRes = new ArrayList<>();
        List<ListInfo> listInfos = baseMapper.selectList(new QueryWrapper<>());
        for (ListInfo listInfo : listInfos) {
            ListAllResVO listAllResVO = new ListAllResVO();
            BeanUtils.copyProperties(listInfo, listAllResVO);
            listAllRes.add(listAllResVO);
        }
        return listAllRes;
    }
}

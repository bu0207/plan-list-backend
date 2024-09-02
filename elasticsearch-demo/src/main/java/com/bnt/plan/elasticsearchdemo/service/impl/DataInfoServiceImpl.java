package com.bnt.plan.elasticsearchdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnt.plan.elasticsearchdemo.entity.DataInfo;
import com.bnt.plan.elasticsearchdemo.entity.DataInfoEs;
import com.bnt.plan.elasticsearchdemo.mapper.DataInfoMapper;
import com.bnt.plan.elasticsearchdemo.repository.DataInfoRepository;
import com.bnt.plan.elasticsearchdemo.service.DataInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bnt
 * @since 2024-08-31
 */
@Service
public class DataInfoServiceImpl implements DataInfoService {

    @Autowired
    private DataInfoMapper dataInfoMapper;
    @Autowired
    private DataInfoRepository dataInfoRepository;

    @Override
    public List<DataInfo> getDataInfoList() {
        QueryWrapper<DataInfo> query = Wrappers.query();
        return dataInfoMapper.selectList(query);
    }

    @Override
    public void save(DataInfoEs dataInfoEs) {
        dataInfoRepository.save(dataInfoEs);
    }

    @Override
    public DataInfoEs getById(Integer id) {
        return dataInfoRepository.findById(id).orElse(new DataInfoEs());
    }

    @Override
    public void deleteById(Integer id) {
        dataInfoRepository.deleteById(id);
    }

    @Override
    public long count() {
        return dataInfoRepository.count();
    }

    @Override
    public boolean existsById(Integer id) {
        return dataInfoRepository.existsById(id);
    }

    @Override
    public List<SearchHit<DataInfoEs>> findByTitleOrContent(String title, String content) {
        return Collections.emptyList();
    }
}

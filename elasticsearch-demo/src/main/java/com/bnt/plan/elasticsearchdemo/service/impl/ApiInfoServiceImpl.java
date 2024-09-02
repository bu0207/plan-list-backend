package com.bnt.plan.elasticsearchdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bnt.plan.elasticsearchdemo.entity.ApiInfo;
import com.bnt.plan.elasticsearchdemo.entity.ApiInfoEs;
import com.bnt.plan.elasticsearchdemo.mapper.ApiInfoMapper;
import com.bnt.plan.elasticsearchdemo.repository.ApiInfoRepository;
import com.bnt.plan.elasticsearchdemo.service.ApiInfoService;
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
public class ApiInfoServiceImpl implements ApiInfoService {

    @Autowired
    private ApiInfoMapper apiInfoMapper;
    @Autowired
    private ApiInfoRepository apiInfoRepository;

    @Override
    public List<ApiInfo> getList() {
        QueryWrapper<ApiInfo> queryWrapper = new QueryWrapper<>();
        return apiInfoMapper.selectList(queryWrapper);
    }

    @Override
    public void save(ApiInfoEs apiInfoEs) {
        apiInfoRepository.save(apiInfoEs);
    }

    @Override
    public ApiInfoEs getApiInfoEs(Integer id) {
        return apiInfoRepository.findById(id).orElse(new ApiInfoEs());
    }

    @Override
    public void deleteById(Integer id) {
        apiInfoRepository.deleteById(id);
    }

    @Override
    public long count() {
        return apiInfoRepository.count();
    }

    @Override
    public boolean existsById(Integer id) {
        return apiInfoRepository.existsById(id);
    }

    @Override
    public List<SearchHit<ApiInfoEs>> findByTitleOrContent(String title, String content) {
        return apiInfoRepository.findByApiTitleOrApiContent(title, content);
    }
}

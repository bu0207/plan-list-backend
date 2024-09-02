package com.bnt.plan.elasticsearchdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bnt.plan.elasticsearchdemo.entity.ApiInfo;
import com.bnt.plan.elasticsearchdemo.entity.ApiInfoEs;
import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bnt
 * @since 2024-08-31
 */
public interface ApiInfoService {

    List<ApiInfo> getList();

    void save(ApiInfoEs apiInfoEs);

    ApiInfoEs getApiInfoEs(Integer id);

    void deleteById(Integer id);

    long count();

    boolean existsById(Integer id);

    List<SearchHit<ApiInfoEs>> findByTitleOrContent(String title, String content);
}

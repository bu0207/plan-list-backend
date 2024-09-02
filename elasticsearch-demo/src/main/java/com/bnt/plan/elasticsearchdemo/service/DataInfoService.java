package com.bnt.plan.elasticsearchdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bnt.plan.elasticsearchdemo.entity.ApiInfoEs;
import com.bnt.plan.elasticsearchdemo.entity.DataInfo;
import com.bnt.plan.elasticsearchdemo.entity.DataInfoEs;
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
public interface DataInfoService {

    List<DataInfo> getDataInfoList();

    void save(DataInfoEs dataInfoEs);

    DataInfoEs getById(Integer id);

    void deleteById(Integer id);

    long count();

    boolean existsById(Integer id);

    List<SearchHit<DataInfoEs>> findByTitleOrContent(String title, String content);
}

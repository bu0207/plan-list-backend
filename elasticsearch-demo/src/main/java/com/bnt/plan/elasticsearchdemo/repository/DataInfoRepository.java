package com.bnt.plan.elasticsearchdemo.repository;

import com.bnt.plan.elasticsearchdemo.entity.DataInfoEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * TODO
 *
 * @author bnt
 * @version 1.0.0
 * @create 2024/9/2 上午7:05 bnt
 * @history
 */
public interface DataInfoRepository extends ElasticsearchRepository<DataInfoEs, Integer> {

}

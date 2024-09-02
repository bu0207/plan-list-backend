package com.bnt.plan.elasticsearchdemo;

import com.bnt.plan.elasticsearchdemo.entity.ApiInfoEs;
import com.bnt.plan.elasticsearchdemo.entity.DataInfoEs;
import com.bnt.plan.elasticsearchdemo.repository.DataInfoRepository;
import com.bnt.plan.elasticsearchdemo.service.ApiInfoService;
import com.bnt.plan.elasticsearchdemo.service.DataInfoService;
import com.bnt.plan.elasticsearchdemo.util.RedisLockUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

/**
 * TODO
 *
 * @author bnt
 * @version 1.0.0
 * @create 2024/9/1 上午12:26 bnt
 * @history
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticsearchDemoApplication.class)
public class EsTest {
    @Autowired
    private ApiInfoService apiInfoService;
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private DataInfoService dataInfoService;
    @Autowired
    private RedisLockUtils redisLockUtils;

    @Test
    public void saveElasticSearch(){
        ApiInfoEs apiInfoEs = new ApiInfoEs();
        apiInfoEs.setId(1);
        apiInfoEs.setApiName("测试接口2");
        apiInfoEs.setApiTitle("测试接口标题2");
        apiInfoEs.setApiContent("测试接口内容2");
        apiInfoService.save(apiInfoEs);
    }

    @Test
    public void saveDataInfo(){
        DataInfoEs dataInfoEs = new DataInfoEs();
        dataInfoEs.setDataName("测试数据2");
        dataInfoEs.setDataTitle("测试数据标题2");
        dataInfoEs.setDataContent("测试数据内容2");
        dataInfoEs.setDataUpdate(LocalDateTime.now());
        dataInfoService.save(dataInfoEs);
    }

    @Test
    public void findById(){
        ApiInfoEs apiInfoEs = apiInfoService.getApiInfoEs(1);
        System.out.println(apiInfoEs);
    }

    @Test
    public void deleteById(){
        apiInfoService.deleteById(1);
    }

    @Test
    public void count(){
        System.out.println(apiInfoService.count());
    }

    @Test
    public void existsById(){
        System.out.println(apiInfoService.existsById(1));
    }

    @Test
    public void findByTitleOrContent(){
        List<SearchHit<ApiInfoEs>> titleOrContent = apiInfoService.findByTitleOrContent("测试接口", "测试接口内容");
        for (SearchHit<ApiInfoEs> apiInfoEsSearchHit : titleOrContent) {
            List<String> apiTitle = apiInfoEsSearchHit.getHighlightField("apiTitle");
            System.out.println(apiTitle);
            List<String> apiContent = apiInfoEsSearchHit.getHighlightField("apiContent");
            System.out.println(apiContent);
        }
    }


    /**
     * 单条件精确查询
     * @throws IOException
     */
    @Test
    public void search0() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(QueryBuilders.termsQuery("apiName", "赵里"));

        //搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("api_info");
        searchRequest.types("_doc");
        searchRequest.source(builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        // 解析查询结果
        System.out.println(response.toString());
    }


    /**
     * 多字段匹配
     */
    @Test
    public void singleUserId() {
        NativeSearchQuery build = new NativeSearchQueryBuilder().withQuery(multiMatchQuery("测试","apiTitle","dataTitle")).build();
        System.out.println(elasticsearchRestTemplate.search(build, ApiInfoEs.class, IndexCoordinates.of("api_info","data_info")));
    }

    @Test
    public void setRedisLock(){
        boolean lock = redisLockUtils.lock("test", "true", 60L);
        if (lock){
            try {
                System.out.println("加锁成功");
                System.out.println("执行业务逻辑");
            }finally {
                redisLockUtils.unlock("test");
            }
        }
    }


}

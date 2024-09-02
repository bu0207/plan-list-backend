package com.bnt.plan.elasticsearchdemo.repository;

import com.bnt.plan.elasticsearchdemo.entity.ApiInfoEs;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO
 *
 * @author bnt
 * @version 1.0.0
 * @create 2024/9/1 上午12:07 bnt
 * @history
 */
@Repository
public interface ApiInfoRepository extends ElasticsearchRepository<ApiInfoEs, Integer> {

    @Highlight(
            fields = {@HighlightField(name = "apiTitle"), @HighlightField(name = "apiContent")},
            parameters = @HighlightParameters(preTags = "<span style='color:red'>", postTags = "</span>", numberOfFragments = 0)
    )
    List<SearchHit<ApiInfoEs>> findByApiTitleOrApiContent(String title, String content);
}

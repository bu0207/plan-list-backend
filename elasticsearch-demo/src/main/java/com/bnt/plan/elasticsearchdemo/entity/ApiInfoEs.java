package com.bnt.plan.elasticsearchdemo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * TODO
 *
 * @author bnt
 * @version 1.0.0
 * @create 2024/8/31 下午11:47 bnt
 * @history
 */
@Data
@Document(indexName = "api_info")
public class ApiInfoEs {
    @Field(store = true, type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String apiName;

    @Field(store = true, type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String apiContent;

    @Field(store = true, type = FieldType.Date)
    private LocalDateTime updateTime;

    @Field(store = true, type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String apiTitle;

    @Id
    @Field(store = true, type = FieldType.Integer, index = false)
    private Integer id;
}

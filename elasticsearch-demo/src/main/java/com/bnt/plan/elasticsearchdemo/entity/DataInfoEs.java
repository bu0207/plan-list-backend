package com.bnt.plan.elasticsearchdemo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author bnt
 * @since 2024-08-31
 */
@Data
@Document(indexName = "data_info")
public class DataInfoEs implements Serializable {

    @Field(store = true, type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String dataName;

    @Field(store = true, type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String dataContent;

    @Field(store = true, type = FieldType.Date)
    private LocalDateTime dataUpdate;

    @Field(store = true, type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String dataTitle;

    @Id
    @Field(store = true, type = FieldType.Integer, index = false)
    private Integer id;


}

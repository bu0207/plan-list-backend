package com.bnt.plan.elasticsearchdemo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
@TableName("api_info")
public class ApiInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("api_name")
    private String apiName;

    @TableField("api_content")
    private String apiContent;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("api_title")
    private String apiTitle;

    @TableField("id")
    private Integer id;


}

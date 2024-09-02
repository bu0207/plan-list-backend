package com.bnt.plan.elasticsearchdemo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author bnt
 * @since 2024-08-31
 */
@Getter
@Setter
@TableName("data_info")
public class DataInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("data_name")
    private String dataName;

    @TableField("data_content")
    private String dataContent;

    @TableField("data_update")
    private LocalDateTime dataUpdate;

    @TableField("data_title")
    private String dataTitle;

    @TableField("id")
    private Integer id;


}

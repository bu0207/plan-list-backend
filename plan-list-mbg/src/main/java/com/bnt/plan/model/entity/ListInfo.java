package com.bnt.plan.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 清单表
 * </p>
 *
 * @author bnt
 * @since 2023-09-22
 */
@Getter
@Setter
@TableName("list_info")
@ApiModel(value = "ListInfo对象", description = "清单表")
public class ListInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @ApiModelProperty("创建人")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty("修改时间")
    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

    @ApiModelProperty("修改人")
    @TableField("update_by")
    private String updateBy;

    @ApiModelProperty("删除标记;0-有效 1-删除")
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private String delFlag;

    @ApiModelProperty("清单名称")
    @TableField("list_name")
    private String listName;

    @ApiModelProperty("颜色")
    @TableField("list_color")
    private String listColor;

    @ApiModelProperty("类型;0-任务清单 1-笔记清单")
    @TableField("list_type")
    private String listType;


}

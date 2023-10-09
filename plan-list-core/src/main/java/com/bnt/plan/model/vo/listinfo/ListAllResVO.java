package com.bnt.plan.model.vo.listinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 清单列表
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/10/9 15:50 bnt
 * @history
 */
@ApiModel("清单列表")
@Data
public class ListAllResVO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("清单名称")
    private String listName;

    @ApiModelProperty("颜色")
    private String listColor;

    @ApiModelProperty("清单类型;0-任务清单 1-笔记清单")
    private String listType;
}

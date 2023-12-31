package com.bnt.plan.model.vo.listinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 清单
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/9/22 17:55 bnt
 * @history
 */
@ApiModel("清单新增")
@Data
public class ListInfoReqVO {
    @ApiModelProperty("清单名称")
    @NotBlank(message = "清单名称不能为空")
    private String listName;

    @ApiModelProperty("颜色")
    private String listColor;

    @ApiModelProperty("类型;0-任务清单 1-笔记清单")
    @NotBlank(message = "清单类型不能为空")
    private String listType;
}

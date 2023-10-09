package com.bnt.plan.model.vo.listinfo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 清单修改
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/10/9 15:31 bnt
 * @history
 */
@ApiModel("清单修改")
@Data
public class ListUpdateReqVO extends ListInfoReqVO {

    @NotBlank(message = "id不能为空")
    private String id;
}

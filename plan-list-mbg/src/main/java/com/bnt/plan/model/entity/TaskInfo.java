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
 * 任务表
 * </p>
 *
 * @author bnt
 * @since 2023-09-22
 */
@Getter
@Setter
@TableName("task_info")
@ApiModel(value = "TaskInfo对象", description = "任务表")
public class TaskInfo implements Serializable {

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

    @ApiModelProperty("父任务ID")
    @TableField("task_parent_id")
    private String taskParentId;

    @ApiModelProperty("任务名称")
    @TableField("task_name")
    private String taskName;

    @ApiModelProperty("任务描述")
    @TableField("task_desc")
    private String taskDesc;

    @ApiModelProperty("时间")
    @TableField("task_date")
    private LocalDateTime taskDate;

    @ApiModelProperty("优先级;0-无 1-低 2-中 3-高")
    @TableField("task_priority")
    private String taskPriority;

    @ApiModelProperty("标签")
    @TableField("task_tag")
    private String taskTag;

    @ApiModelProperty("状态;0-未完成 1-已完成 2-已放弃")
    @TableField("task_status")
    private String taskStatus;

    @ApiModelProperty("指派人员")
    @TableField("task_member")
    private String taskMember;


}

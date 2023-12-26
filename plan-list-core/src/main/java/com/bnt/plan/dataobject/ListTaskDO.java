package com.bnt.plan.dataobject;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * 清单任务
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/12/18 11:55 bnt
 * @history
 */
@Data
@Document("list_task")
public class ListTaskDO {

    @Id
    private Integer id;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务描述
     */
    private String taskDesc;
    /**
     * 时间
     */
    private LocalDateTime taskDate;
    /**
     * 优先级;0-无 1-低 2-中 3-高
     */
    private Integer taskPriority;
    /**
     * 标签
     */
    private String taskTag;
    /**
     * 状态;0-未完成 1-已完成 2-已放弃
     */
    private String taskStatus;
    /**
     * 指派人员
     */
    private String taskMember;
    /**
     * 父任务ID
     */
    private Integer parentId;

    /**
     * 任务类型 0-任务 1-笔记
     */
    private Integer taskType;
    /**
     * 检查事项
     */
    private String checkItem;
}

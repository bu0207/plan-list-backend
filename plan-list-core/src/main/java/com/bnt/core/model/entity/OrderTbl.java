package com.bnt.core.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author bnt
 * @since 2023-09-11
 */
@Getter
@Setter
@TableName("order_tbl")
public class OrderTbl implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private String userId;

    @TableField("commodity_code")
    private String commodityCode;

    @TableField("count")
    private Integer count;

    @TableField("money")
    private Integer money;


}

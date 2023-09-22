package com.bnt.plan.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 已登录用户视图（脱敏）
 *
 *  
 *  
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserVO implements Serializable {

    /**
     * 用户 id
     */
    private String id;

    private String token;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
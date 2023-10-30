package com.bnt.plan.enumlist;

/**
 * TODO
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/10/30 14:34 bnt
 * @history
 */
public enum StatusEnum {

    /**
     * 已删除
     */
    DELETED("1", "已删除"),

    /**
     * 正常
     */
    NORMAL("0", "正常"),

    /**
     * 停用
     */
    DISABLED("2", "停用");

    StatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private String code;
    private String name;
}

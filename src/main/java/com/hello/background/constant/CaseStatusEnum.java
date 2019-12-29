package com.hello.background.constant;

/**
 * @author wuketao
 * @date 2019/12/28
 * @Description
 */
public enum CaseStatusEnum {
    PREPARE("prepare", "准备"),
    DOING("doing", "进行中"),
    FINISH("finish", "完成"),
    PAUSE("pause", "暂停"),
    CLOSE("close", "关闭");

    private String code;
    private String desc;

    private CaseStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

package com.hello.background.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuketao
 * @date 2019/12/28
 * @Description
 */
public enum CaseStatusEnum {
    PREPARE("PREPARE", "准备"),
    DOING("DOING", "进行中"),
    FINISH("FINISH", "完成"),
    PAUSE("PAUSE", "暂停"),
    CLOSE("CLOSE", "关闭");

    public static Map<String, CaseStatusEnum> LOOP = new HashMap<>();

    static {
        CaseStatusEnum[] values = CaseStatusEnum.values();
        for (int i = 0; i < values.length; i++) {
            LOOP.put(values[i].code, values[i]);
        }
    }

    private String code;
    private String desc;

    private CaseStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

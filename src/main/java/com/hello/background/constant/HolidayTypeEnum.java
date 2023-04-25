package com.hello.background.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 假期类型
 *
 * @author wuketao
 * @date 2019/12/28
 * @Description
 */
public enum HolidayTypeEnum {
    SHI("SHI", "事假"),
    BING("BING", "病假"),
    NIAN("NIAN", "年假"),
    CHAN("CHAN", "产假"),
    PEICHAN("PEICHAN", "陪产假"),
    SANG("SANG", "丧假");

    public static Map<String, HolidayTypeEnum> LOOP = new HashMap<>();

    static {
        HolidayTypeEnum[] values = HolidayTypeEnum.values();
        for (int i = 0; i < values.length; i++) {
            LOOP.put(values[i].code, values[i]);
        }
    }

    private String code;
    private String desc;

    HolidayTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

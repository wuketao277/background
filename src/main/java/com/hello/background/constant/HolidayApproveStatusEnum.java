package com.hello.background.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 假期审批状态
 *
 * @author wuketao
 * @date 2019/12/28
 * @Description
 */
public enum HolidayApproveStatusEnum {
    APPLY("APPLY", "申请"),
    APPROVED("APPROVED", "审批通过"),
    DENY("DENY", "否决");

    public static Map<String, HolidayApproveStatusEnum> LOOP = new HashMap<>();

    static {
        HolidayApproveStatusEnum[] values = HolidayApproveStatusEnum.values();
        for (int i = 0; i < values.length; i++) {
            LOOP.put(values[i].code, values[i]);
        }
    }

    private String code;
    private String desc;

    HolidayApproveStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

package com.hello.background.vo;

import lombok.Data;

/**
 * @author wuketao
 * @date 2022/4/17
 * @Description
 */
@Data
public class QueryGeneralReportResponseKeyValue {
    private String name;
    private Integer value;

    public QueryGeneralReportResponseKeyValue(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}

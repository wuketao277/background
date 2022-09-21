package com.hello.background.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wuketao
 * @date 2022/4/17
 * @Description
 */
@Data
public class QueryGeneralReportResponseKeyValue {
    private String name;
    private BigDecimal value;

    public QueryGeneralReportResponseKeyValue(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }
}

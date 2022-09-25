package com.hello.background.constant;

import java.math.BigDecimal;

/**
 * @author wuketao
 * @date 2022/9/25
 * @Description
 */
public enum InvoiceType {
    Z3(new BigDecimal(3), "3%专票"),
    Z6(new BigDecimal(6), "6%专票"),
    P3(new BigDecimal(3), "3%普票"),
    P6(new BigDecimal(6), "6%普票");
    /**
     * 费率
     */
    private BigDecimal rate;
    /**
     * 名称
     */
    private String name;

    /**
     * 构造函数
     *
     * @param _rate
     * @param _name
     */
    InvoiceType(BigDecimal _rate, String _name) {
        this.rate = _rate;
        this.name = _name;
    }
}

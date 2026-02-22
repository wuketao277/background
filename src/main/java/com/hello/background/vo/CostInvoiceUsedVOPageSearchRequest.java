package com.hello.background.vo;

import lombok.Data;

/**
 * 成本发票使用情况分页查询
 *
 * @author wuketao
 * @date 2026/02/22
 * @Description
 */
@Data
public class CostInvoiceUsedVOPageSearchRequest {
    /**
     * 顾问登录名
     */
    private String consultantUserName;
}

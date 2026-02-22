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
public class CostInvoiceUsedVOPageRequest {
    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 页尺寸
     */
    private Integer pageSize;
    /**
     * 搜索条件
     */
    private CostInvoiceUsedVOPageSearchRequest search;
}

package com.hello.background.vo;

import lombok.Data;

/**
 * 发票分页查询
 *
 * @author wuketao
 * @date 2021/11/20
 * @Description
 */
@Data
public class InvoiceVOPageRequest {
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
    private InvoiceVOPageSearchRequest search;
}

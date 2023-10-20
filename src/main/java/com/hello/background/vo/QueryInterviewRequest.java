package com.hello.background.vo;

import lombok.Data;

/**
 * @author wuketao
 * @date 2023/10/16
 * @Description
 */
@Data
public class QueryInterviewRequest {
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
    private QueryInterviewSearchRequest search;
}

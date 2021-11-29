package com.hello.background.vo;

import lombok.Data;

/**
 * @author wuketao
 * @date 2021/11/20
 * @Description
 */
@Data
public class SuccessfulPermVOPageRequest {
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
    private SuccessfulPermVOPageSearchRequest search;
}

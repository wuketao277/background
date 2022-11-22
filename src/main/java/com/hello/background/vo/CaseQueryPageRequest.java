package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuketao
 * @date 2022/11/22
 * @Description
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CaseQueryPageRequest {
    private Integer currentPage;
    private Integer pageSize;
    private CaseQueryPageRequestSearch search;
}

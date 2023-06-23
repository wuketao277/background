package com.hello.background.vo;

import lombok.Data;

/**
 * @author wuketao
 * @date 2023/6/22
 * @Description
 */
@Data
public class KPIWorkDaysAdjustRequest {
    private String search;
    private Integer currentPage;
    private Integer pageSize;
}

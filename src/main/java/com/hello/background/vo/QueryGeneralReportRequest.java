package com.hello.background.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author wuketao
 * @date 2022/1/12
 * @Description
 */
@Data
public class QueryGeneralReportRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}

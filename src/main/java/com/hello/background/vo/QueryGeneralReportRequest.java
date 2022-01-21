package com.hello.background.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author wuketao
 * @date 2022/1/12
 * @Description
 */
@Data
public class QueryGeneralReportRequest {
    private Date startDate;
    private Date endDate;
}

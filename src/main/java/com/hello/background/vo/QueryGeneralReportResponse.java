package com.hello.background.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuketao
 * @date 2022/1/12
 * @Description
 */
@Data
public class QueryGeneralReportResponse {
    private Integer offerDateBilling = 0;
    private Integer paymentDateBilling = 0;
    private List<QueryGeneralReportResponseKeyValue> offerDateData = new ArrayList<>();
    private List<QueryGeneralReportResponseKeyValue> paymentDateData = new ArrayList<>();
}

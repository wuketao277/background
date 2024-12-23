package com.hello.background.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuketao
 * @date 2022/1/12
 * @Description
 */
@Data
public class QueryGeneralReportResponse {
    private BigDecimal offerDateBilling = BigDecimal.ZERO;
    private BigDecimal paymentDateBilling = BigDecimal.ZERO;
    private BigDecimal actualPaymentDateBilling = BigDecimal.ZERO;
    private BigDecimal unactualPaymentDateBilling = BigDecimal.ZERO;
    private BigDecimal invoiceDateBilling = BigDecimal.ZERO;
    private List<QueryGeneralReportResponseKeyValue> offerDateData = new ArrayList<>();
    private List<QueryGeneralReportResponseKeyValue> paymentDateData = new ArrayList<>();
    private List<QueryGeneralReportResponseKeyValue> actualPaymentDateData = new ArrayList<>();
    private List<QueryGeneralReportResponseKeyValue> unactualPaymentDateData = new ArrayList<>();
    private List<QueryGeneralReportResponseKeyValue> personalOfferData = new ArrayList<>();
    private List<QueryGeneralReportResponseKeyValue> invoiceDateData = new ArrayList<>();
    private List<QueryGeneralReportResponseKeyValue> personalReceiveData = new ArrayList<>();
    private List<QueryGeneralReportResponseKeyValue> recruiterOfferBillingData = new ArrayList<>();
    private List<QueryGeneralReportResponseKeyValue> recruiterMonthlyOfferBillingData = new ArrayList<>();
    private List<QueryGeneralReportResponseKeyValue> teamOfferGPData = new ArrayList<>();
    private List<QueryGeneralReportResponseKeyValue> teamMonthlyOfferGPData = new ArrayList<>();
    private List<QueryGeneralReportResponseKeyValue> avgOfferData = new ArrayList<>();
    // 未来收款X轴数据
    private List<String> futureReceiveBillingDataX = new ArrayList<>();
    // 未来收款Y轴数据
    private List<BigDecimal> futureReceiveBillingDataY = new ArrayList<>();
    // 未来收款X轴数据，不包含一汽的数据
    private List<String> futureReceiveBillingDataX2 = new ArrayList<>();
    // 未来收款Y轴数据，不包含一汽的数据
    private List<BigDecimal> futureReceiveBillingDataY2 = new ArrayList<>();
}

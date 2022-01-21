package com.hello.background.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wuketao
 * @date 2022/1/12
 * @Description
 */
@Data
public class QueryGeneralReportResponse {
    private List<String> personalRateOptionDataX;
    private List<BigDecimal> personalRateOptionDataY;
    private List<String> clientRateOptionDataX;
    private List<BigDecimal> clientRateOptionDataY;
}

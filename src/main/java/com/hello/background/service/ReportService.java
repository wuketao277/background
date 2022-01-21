package com.hello.background.service;

import com.hello.background.domain.SuccessfulPerm;
import com.hello.background.repository.SuccessfulPermRepository;
import com.hello.background.vo.QueryGeneralReportRequest;
import com.hello.background.vo.QueryGeneralReportResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 报告服务
 *
 * @author wuketao
 * @date 2019/12/14
 * @Description
 */
@Transactional
@Slf4j
@Service
public class ReportService {
    @Autowired
    private SuccessfulPermRepository successfulPermRepository;

    public QueryGeneralReportResponse queryGeneral(QueryGeneralReportRequest request) {
        Iterable<SuccessfulPerm> allList = successfulPermRepository.findAll();
        QueryGeneralReportResponse response = new QueryGeneralReportResponse();
        List<String> personalRateOptionDataX = new ArrayList<>();
        List<BigDecimal> personalRateOptionDataY = new ArrayList<>();
        List<String> clientRateOptionDataX = new ArrayList<>();
        List<BigDecimal> clientRateOptionDataY = new ArrayList<>();
        personalRateOptionDataX.add("Leon");
        personalRateOptionDataX.add("Arran");
        personalRateOptionDataX.add("Ellen");
        personalRateOptionDataY.add(BigDecimal.valueOf(12000.1));
        personalRateOptionDataY.add(BigDecimal.valueOf(1));
        personalRateOptionDataY.add(BigDecimal.valueOf(0.1));
        response.setClientRateOptionDataX(clientRateOptionDataX);
        response.setClientRateOptionDataY(clientRateOptionDataY);
        response.setPersonalRateOptionDataX(personalRateOptionDataX);
        response.setPersonalRateOptionDataY(personalRateOptionDataY);
        return response;
    }
}

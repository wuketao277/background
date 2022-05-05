package com.hello.background.service;

import com.hello.background.domain.SuccessfulPerm;
import com.hello.background.repository.SuccessfulPermRepository;
import com.hello.background.vo.QueryGeneralReportRequest;
import com.hello.background.vo.QueryGeneralReportResponse;
import com.hello.background.vo.QueryGeneralReportResponseKeyValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
        QueryGeneralReportResponse response = new QueryGeneralReportResponse();
        try {
            Iterable<SuccessfulPerm> iterable = successfulPermRepository.findAll();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = sdf.parse(String.format("%s-%s-%s %s:%s:%s", request.getStartDate().getYear(), request.getStartDate().getMonthValue(), request.getStartDate().getDayOfMonth(), 0, 0, 0));
            Date endDate = sdf.parse(String.format("%s-%s-%s %s:%s:%s", request.getEndDate().getYear(), request.getEndDate().getMonthValue(), request.getEndDate().getDayOfMonth(), 23, 59, 59));
            // offer signed 数据
            generateOfferSignedData(iterable, startDate, endDate, response);
            // payment date 数据
            generatePaymentDateData(iterable, startDate, endDate, response);
            // 个人 gp数据
            generatePersonalOfferData(iterable, startDate, endDate, response);
            // 个人 gp数据倒排序展示
            response.getPersonalOfferData().sort(Comparator.comparing(QueryGeneralReportResponseKeyValue::getValue).reversed());

        } catch (Exception ex) {
        }
        return response;
    }

    /**
     * 生成payment date数据
     *
     * @param iterable
     * @param startDate
     * @param endDate
     * @param response
     */
    private void generatePaymentDateData(Iterable<SuccessfulPerm> iterable, Date startDate, Date endDate, QueryGeneralReportResponse response) {
        Iterator<SuccessfulPerm> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            SuccessfulPerm s = iterator.next();
            if (s.getPaymentDate() != null
                    && s.getPaymentDate().compareTo(startDate) >= 0
                    && endDate.compareTo(s.getPaymentDate()) >= 0) {
                response.getPaymentDateData().add(new QueryGeneralReportResponseKeyValue(s.getCandidateChineseName(), s.getBilling()));
                response.setPaymentDateBilling(response.getPaymentDateBilling() + s.getBilling());
                if (null != s.getActualPaymentDate()) {
                    // 已付款
                    response.getActualPaymentDateData().add(new QueryGeneralReportResponseKeyValue(s.getCandidateChineseName(), s.getBilling()));
                    response.setActualPaymentDateBilling(response.getActualPaymentDateBilling() + s.getBilling());
                } else {
                    // 未付款
                    response.getUnactualPaymentDateData().add(new QueryGeneralReportResponseKeyValue(s.getCandidateChineseName(), s.getBilling()));
                    response.setUnactualPaymentDateBilling(response.getUnactualPaymentDateBilling() + s.getBilling());
                }
            }
        }
    }

    /**
     * 生成offer signed数据
     *
     * @param iterable
     * @param startDate
     * @param endDate
     * @param response
     */
    private void generateOfferSignedData(Iterable<SuccessfulPerm> iterable, Date startDate, Date endDate, QueryGeneralReportResponse response) {
        Iterator<SuccessfulPerm> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            SuccessfulPerm s = iterator.next();
            if (s.getOfferDate() != null
                    && s.getOfferDate().compareTo(startDate) >= 0
                    && endDate.compareTo(s.getOfferDate()) >= 0) {
                response.getOfferDateData().add(new QueryGeneralReportResponseKeyValue(s.getCandidateChineseName(), s.getBilling()));
                response.setOfferDateBilling(response.getOfferDateBilling() + s.getBilling());
            }
        }
    }

    /**
     * 生成个人offer数据
     *
     * @param iterable
     * @param startDate
     * @param endDate
     * @param response
     */
    private void generatePersonalOfferData(Iterable<SuccessfulPerm> iterable, Date startDate, Date endDate, QueryGeneralReportResponse response) {
        Map<String, Integer> personalGpMap = new HashMap<>();
        Iterator<SuccessfulPerm> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            SuccessfulPerm s = iterator.next();
            if (s.getOfferDate() != null
                    && s.getOfferDate().compareTo(startDate) >= 0
                    && endDate.compareTo(s.getOfferDate()) >= 0) {
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName(), s.getConsultantCommissionPercent());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName2(), s.getConsultantCommissionPercent2());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName3(), s.getConsultantCommissionPercent3());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName4(), s.getConsultantCommissionPercent4());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName5(), s.getConsultantCommissionPercent5());
            }
        }
        for (Map.Entry<String, Integer> entry : personalGpMap.entrySet()) {
            response.getPersonalOfferData().add(new QueryGeneralReportResponseKeyValue(entry.getKey(), entry.getValue()));
        }
    }

    /**
     * 计算每个顾问在成功case中的gp
     *
     * @param personalGpMap
     * @param s
     * @param consultantName
     * @param consultantCommissionPercent
     */
    private void calcPersonalGp(Map<String, Integer> personalGpMap, SuccessfulPerm s, String consultantName, Integer consultantCommissionPercent) {
        if (Strings.isNotBlank(consultantName) && null != consultantCommissionPercent) {
            Integer totalPercent = 0
                    + (s.getConsultantCommissionPercent() != null ? s.getConsultantCommissionPercent() : 0)
                    + (s.getConsultantCommissionPercent2() != null ? s.getConsultantCommissionPercent2() : 0)
                    + (s.getConsultantCommissionPercent3() != null ? s.getConsultantCommissionPercent3() : 0)
                    + (s.getConsultantCommissionPercent4() != null ? s.getConsultantCommissionPercent4() : 0)
                    + (s.getConsultantCommissionPercent5() != null ? s.getConsultantCommissionPercent5() : 0);
            if (totalPercent > 0) {
                Integer personalGp = BigDecimal.valueOf(consultantCommissionPercent).divide(BigDecimal.valueOf(totalPercent), 10, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(s.getGp())).intValue();
                personalGpMap.put(consultantName, personalGp + (personalGpMap.get(consultantName) != null ? personalGpMap.get(consultantName) : 0));
            }
        }
    }
}

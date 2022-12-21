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
            List<String> approveStatusList = new ArrayList<>();
            approveStatusList.add("applied");
            approveStatusList.add("approved");
            // 查询申请和审批通过的数据
            Iterable<SuccessfulPerm> iterable = successfulPermRepository.findByApproveStatusIn(approveStatusList);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = sdf.parse(String.format("%s-%s-%s %s:%s:%s", request.getStartDate().getYear(), request.getStartDate().getMonthValue(), request.getStartDate().getDayOfMonth(), 0, 0, 0));
            Date endDate = sdf.parse(String.format("%s-%s-%s %s:%s:%s", request.getEndDate().getYear(), request.getEndDate().getMonthValue(), request.getEndDate().getDayOfMonth(), 23, 59, 59));
            // offer signed 数据
            generateOfferSignedData(iterable, startDate, endDate, response);
            // payment date 数据
            generatePaymentDateData(iterable, startDate, endDate, response);
            // 个人 gp数据
            generatePersonalOfferData(iterable, startDate, endDate, response);
            // Invoice Date 数据
            generateInvoiceDateData(iterable, startDate, endDate, response);
            // 个人 gp到账数据
            generatePersonalReceiveData(iterable, startDate, endDate, response);
        } catch (Exception ex) {
        }
        return response;
    }

    /**
     * 生成Invoice Date数据
     *
     * @param iterable
     * @param startDate
     * @param endDate
     * @param response
     */
    private void generateInvoiceDateData(Iterable<SuccessfulPerm> iterable, Date startDate, Date endDate, QueryGeneralReportResponse response) {
        Iterator<SuccessfulPerm> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            SuccessfulPerm s = iterator.next();
            if (s.getInvoiceDate() != null
                    && s.getInvoiceDate().compareTo(startDate) >= 0
                    && endDate.compareTo(s.getInvoiceDate()) >= 0) {
                response.getInvoiceDateData().add(new QueryGeneralReportResponseKeyValue(s.getCandidateChineseName(), s.getBilling()));
                response.setInvoiceDateBilling(response.getInvoiceDateBilling().add(s.getBilling()));
            }
        }
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
                // 应到
                response.getPaymentDateData().add(new QueryGeneralReportResponseKeyValue(s.getCandidateChineseName(), s.getBilling()));
                response.setPaymentDateBilling(response.getPaymentDateBilling().add(s.getBilling()));
            }
            if (s.getActualPaymentDate() != null
                    && s.getActualPaymentDate().compareTo(startDate) >= 0
                    && endDate.compareTo(s.getActualPaymentDate()) >= 0) {
                // 已付款
                response.getActualPaymentDateData().add(new QueryGeneralReportResponseKeyValue(s.getCandidateChineseName(), s.getBilling()));
                response.setActualPaymentDateBilling(response.getActualPaymentDateBilling().add(s.getBilling()));
            }
            if (s.getPaymentDate() != null
                    && s.getPaymentDate().compareTo(startDate) >= 0
                    && endDate.compareTo(s.getPaymentDate()) >= 0
                    && s.getActualPaymentDate() == null) {
                // 未付款
                response.getUnactualPaymentDateData().add(new QueryGeneralReportResponseKeyValue(s.getCandidateChineseName(), s.getBilling()));
                response.setUnactualPaymentDateBilling(response.getUnactualPaymentDateBilling().add(s.getBilling()));
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
                // 猎头业务算billing，外包业务算gp
                BigDecimal sum = BigDecimal.ZERO;
                if ("perm".equals(s.getType())) {
                    sum = s.getBilling();
                } else {
                    sum = s.getGp();
                }
                response.getOfferDateData().add(new QueryGeneralReportResponseKeyValue(s.getCandidateChineseName(), sum));
                response.setOfferDateBilling(response.getOfferDateBilling().add(sum));
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
        Map<String, BigDecimal> personalGpMap = new HashMap<>();
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
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName6(), s.getConsultantCommissionPercent6());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName7(), s.getConsultantCommissionPercent7());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName8(), s.getConsultantCommissionPercent8());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName9(), s.getConsultantCommissionPercent9());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName10(), s.getConsultantCommissionPercent10());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName11(), s.getConsultantCommissionPercent11());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName12(), s.getConsultantCommissionPercent12());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName13(), s.getConsultantCommissionPercent13());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName14(), s.getConsultantCommissionPercent14());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName15(), s.getConsultantCommissionPercent15());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName16(), s.getConsultantCommissionPercent16());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName17(), s.getConsultantCommissionPercent17());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName18(), s.getConsultantCommissionPercent18());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName19(), s.getConsultantCommissionPercent19());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName20(), s.getConsultantCommissionPercent20());
            }
        }
        for (Map.Entry<String, BigDecimal> entry : personalGpMap.entrySet()) {
            response.getPersonalOfferData().add(new QueryGeneralReportResponseKeyValue(entry.getKey(), entry.getValue()));
        }
        // 个人 gp数据倒排序展示
        response.getPersonalOfferData().sort(Comparator.comparing(QueryGeneralReportResponseKeyValue::getValue).reversed());
    }

    /**
     * 计算每个顾问在成功case中的gp
     *
     * @param personalGpMap
     * @param s
     * @param consultantName
     * @param consultantCommissionPercent
     */
    private void calcPersonalGp(Map<String, BigDecimal> personalGpMap, SuccessfulPerm s, String consultantName, BigDecimal consultantCommissionPercent) {
        if (Strings.isNotBlank(consultantName) && null != consultantCommissionPercent) {
            BigDecimal totalPercent = BigDecimal.ZERO.add(Optional.ofNullable(s.getConsultantCommissionPercent()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent2()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent3()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent4()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent5()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent6()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent7()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent8()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent9()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent10()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent11()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent12()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent13()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent14()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent15()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent16()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent17()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent18()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent19()).orElse(BigDecimal.ZERO))
                    .add(Optional.ofNullable(s.getConsultantCommissionPercent20()).orElse(BigDecimal.ZERO));
            if (totalPercent.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal personalGp = consultantCommissionPercent.divide(totalPercent, 6, BigDecimal.ROUND_DOWN).multiply(s.getGp());
                personalGpMap.put(consultantName, personalGp.add((personalGpMap.get(consultantName) != null ? personalGpMap.get(consultantName) : BigDecimal.ZERO)));
            }
        }
    }

    /**
     * 生成个人到账数据
     *
     * @param iterable
     * @param startDate
     * @param endDate
     * @param response
     */
    private void generatePersonalReceiveData(Iterable<SuccessfulPerm> iterable, Date startDate, Date endDate, QueryGeneralReportResponse response) {
        Map<String, BigDecimal> personalGpMap = new HashMap<>();
        Iterator<SuccessfulPerm> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            SuccessfulPerm s = iterator.next();
            if (s.getActualPaymentDate() != null
                    && s.getActualPaymentDate().compareTo(startDate) >= 0
                    && endDate.compareTo(s.getActualPaymentDate()) >= 0) {
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName(), s.getConsultantCommissionPercent());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName2(), s.getConsultantCommissionPercent2());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName3(), s.getConsultantCommissionPercent3());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName4(), s.getConsultantCommissionPercent4());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName5(), s.getConsultantCommissionPercent5());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName6(), s.getConsultantCommissionPercent6());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName7(), s.getConsultantCommissionPercent7());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName8(), s.getConsultantCommissionPercent8());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName9(), s.getConsultantCommissionPercent9());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName10(), s.getConsultantCommissionPercent10());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName11(), s.getConsultantCommissionPercent11());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName12(), s.getConsultantCommissionPercent12());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName13(), s.getConsultantCommissionPercent13());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName14(), s.getConsultantCommissionPercent14());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName15(), s.getConsultantCommissionPercent15());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName16(), s.getConsultantCommissionPercent16());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName17(), s.getConsultantCommissionPercent17());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName18(), s.getConsultantCommissionPercent18());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName19(), s.getConsultantCommissionPercent19());
                calcPersonalGp(personalGpMap, s, s.getConsultantUserName20(), s.getConsultantCommissionPercent20());
            }
        }
        for (Map.Entry<String, BigDecimal> entry : personalGpMap.entrySet()) {
            response.getPersonalReceiveData().add(new QueryGeneralReportResponseKeyValue(entry.getKey(), entry.getValue()));
        }
        // 个人 gp到账数据倒排序展示
        response.getPersonalReceiveData().sort(Comparator.comparing(QueryGeneralReportResponseKeyValue::getValue).reversed());
    }
}

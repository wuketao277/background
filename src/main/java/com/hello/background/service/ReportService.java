package com.hello.background.service;

import com.hello.background.constant.RoleEnum;
import com.hello.background.domain.SuccessfulPerm;
import com.hello.background.domain.User;
import com.hello.background.repository.SuccessfulPermRepository;
import com.hello.background.repository.UserRepository;
import com.hello.background.vo.QueryGeneralReportRequest;
import com.hello.background.vo.QueryGeneralReportResponse;
import com.hello.background.vo.QueryGeneralReportResponseKeyValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private UserRepository userRepository;

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
            // Team Offer Data
            // Invoice Date 数据
            generateInvoiceDateData(iterable, startDate, endDate, response);
            // 个人 gp到账数据
            generatePersonalReceiveData(iterable, startDate, endDate, response);
            // Recruiter Offer Billing
            generateRecruiterOfferBillingData(iterable, startDate, endDate, response);
            //  Team Offer GP Data
            generateTeamOfferGPData(response);
        } catch (Exception ex) {
            log.error("queryGeneral", ex);
        }
        return response;
    }

    /**
     * Team Offer GP Data
     *
     * @param response
     */
    private void generateTeamOfferGPData(QueryGeneralReportResponse response) {
        List<User> userList = userRepository.findAll();
        // 先获取个人offer数据
        List<QueryGeneralReportResponseKeyValue> personalOfferData = response.getPersonalOfferData();
        // 遍历个人offer，组成团队offer数据
        for (QueryGeneralReportResponseKeyValue kv : personalOfferData) {
            User user = userList.stream().filter(u -> u.getUsername().equals(kv.getName())).findFirst().get();
            // 检查这个顾问是否属于某一个团队
            Optional<QueryGeneralReportResponseKeyValue> teamOptional = response.getTeamOfferGPData().stream().filter(x -> x.getName().equals(user.getUsername()) || x.getName().equals(user.getTeamLeaderUserName())).findFirst();
            if (teamOptional.isPresent()) {
                // 属于某一个团队
                QueryGeneralReportResponseKeyValue teamKeyValue = teamOptional.get();
                teamKeyValue.setValue(teamKeyValue.getValue().add(kv.getValue()));
            } else {
                // 不属于任意团队，就单独加入
                response.getTeamOfferGPData().add(kv);
            }
        }
        // 按照业绩排序
        response.getTeamOfferGPData().sort(Comparator.comparing(QueryGeneralReportResponseKeyValue::getValue).reversed());
    }

    /**
     * 生成Recruiter Offer Billing数据
     *
     * @param iterable
     * @param startDate
     * @param endDate
     * @param response
     */
    private void generateRecruiterOfferBillingData(Iterable<SuccessfulPerm> iterable, Date startDate, Date endDate, QueryGeneralReportResponse response) {
        // 首先获取所有Recruiter
        List<User> recruiterList = userRepository.findAll().stream().filter(u -> u.getRoles().contains(RoleEnum.RECRUITER)).collect(Collectors.toList());
        for (User user : recruiterList) {
            BigDecimal billingSum = generateRecruiterOfferBillingData(user, iterable, startDate, endDate);
            if (billingSum.compareTo(BigDecimal.ZERO) > 0) {
                // 有Billing 才返回
                response.getRecruiterOfferBillingData().add(new QueryGeneralReportResponseKeyValue(user.getUsername(), billingSum));
            }
        }
        // 按照业绩排序
        response.getRecruiterOfferBillingData().sort(Comparator.comparing(QueryGeneralReportResponseKeyValue::getValue).reversed());
    }

    /**
     * Recruiter 产生的offer billing
     *
     * @param user
     * @param iterable
     * @param startDate
     * @param endDate
     * @return
     */
    private BigDecimal generateRecruiterOfferBillingData(User user, Iterable<SuccessfulPerm> iterable, Date startDate, Date endDate) {
        BigDecimal billingSum = BigDecimal.ZERO;
        // 遍历所有成功case 统计规定时间内 猎头业务的业绩
        Iterator<SuccessfulPerm> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            SuccessfulPerm s = iterator.next();
            if (s.getOfferDate() != null && "perm".equals(s.getType())
                    && s.getOfferDate().compareTo(startDate) >= 0
                    && endDate.compareTo(s.getOfferDate()) >= 0 && (
                    user.getUsername().equals(s.getConsultantUserName()) ||
                            user.getUsername().equals(s.getConsultantUserName2()) ||
                            user.getUsername().equals(s.getConsultantUserName3()) ||
                            user.getUsername().equals(s.getConsultantUserName4()) ||
                            user.getUsername().equals(s.getConsultantUserName5()) ||
                            user.getUsername().equals(s.getConsultantUserName6()) ||
                            user.getUsername().equals(s.getConsultantUserName7()) ||
                            user.getUsername().equals(s.getConsultantUserName8()) ||
                            user.getUsername().equals(s.getConsultantUserName9()) ||
                            user.getUsername().equals(s.getConsultantUserName10()) ||
                            user.getUsername().equals(s.getConsultantUserName11()) ||
                            user.getUsername().equals(s.getConsultantUserName12()) ||
                            user.getUsername().equals(s.getConsultantUserName13()) ||
                            user.getUsername().equals(s.getConsultantUserName14()) ||
                            user.getUsername().equals(s.getConsultantUserName15()) ||
                            user.getUsername().equals(s.getConsultantUserName16()) ||
                            user.getUsername().equals(s.getConsultantUserName17()) ||
                            user.getUsername().equals(s.getConsultantUserName18()) ||
                            user.getUsername().equals(s.getConsultantUserName19()) ||
                            user.getUsername().equals(s.getConsultantUserName20())
            )) {
                if (null != s.getBilling()) {
                    billingSum = billingSum.add(s.getBilling());
                }
            }
        }
        return billingSum;
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
                response.setInvoiceDateBilling(response.getInvoiceDateBilling().add(Optional.ofNullable(s.getBilling()).orElse(BigDecimal.ZERO)));
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
                response.setPaymentDateBilling(response.getPaymentDateBilling().add(Optional.ofNullable(s.getBilling()).orElse(BigDecimal.ZERO)));
            }
            if (s.getActualPaymentDate() != null
                    && s.getActualPaymentDate().compareTo(startDate) >= 0
                    && endDate.compareTo(s.getActualPaymentDate()) >= 0) {
                // 已付款
                response.getActualPaymentDateData().add(new QueryGeneralReportResponseKeyValue(s.getCandidateChineseName(), s.getBilling()));
                response.setActualPaymentDateBilling(response.getActualPaymentDateBilling().add(Optional.ofNullable(s.getBilling()).orElse(BigDecimal.ZERO)));
            }
            if (s.getPaymentDate() != null
                    && s.getPaymentDate().compareTo(startDate) >= 0
                    && endDate.compareTo(s.getPaymentDate()) >= 0
                    && s.getActualPaymentDate() == null) {
                // 未付款
                response.getUnactualPaymentDateData().add(new QueryGeneralReportResponseKeyValue(s.getCandidateChineseName(), s.getBilling()));
                response.setUnactualPaymentDateBilling(response.getUnactualPaymentDateBilling().add(Optional.ofNullable(s.getBilling()).orElse(BigDecimal.ZERO)));
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
                // 猎头业务和咨询业务算billing，外包业务算gp
                BigDecimal sum = BigDecimal.ZERO;
                if ("perm".equals(s.getType()) || "consultation".equals(s.getType())) {
                    sum = Optional.ofNullable(s.getBilling()).orElse(BigDecimal.ZERO);
                } else {
                    sum = Optional.ofNullable(s.getGp()).orElse(BigDecimal.ZERO);
                }
                response.getOfferDateData().add(new QueryGeneralReportResponseKeyValue(s.getCandidateChineseName(), sum));
                response.setOfferDateBilling(response.getOfferDateBilling().add(Optional.ofNullable(sum).orElse(BigDecimal.ZERO)));
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
                personalGpMap.put(consultantName, personalGp.add((personalGpMap.get(consultantName) != null ? personalGpMap.get(consultantName) : BigDecimal.ZERO)).setScale(2, RoundingMode.DOWN));
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

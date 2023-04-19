package com.hello.background.service;

import com.hello.background.constant.RoleEnum;
import com.hello.background.domain.SuccessfulPerm;
import com.hello.background.repository.SuccessfulPermRepository;
import com.hello.background.utils.DateTimeUtil;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.*;
import org.apache.logging.log4j.util.Strings;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author wuketao
 * @date 2020/2/3
 * @Description
 */
@Transactional
@Service
public class SuccessfulPermService {

    @Autowired
    private SuccessfulPermRepository successfulPermRepository;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    public SuccessfulPermVO save(SuccessfulPermVO vo) {
        SuccessfulPerm successfulPerm = new SuccessfulPerm();
        BeanUtils.copyProperties(vo, successfulPerm);
        successfulPerm = successfulPermRepository.save(successfulPerm);
        vo.setId(successfulPerm.getId());
        return vo;
    }

    /**
     * 查询分页
     *
     * @return
     */
    public Page<SuccessfulPermVO> queryPage(SuccessfulPermVOPageRequest request, HttpSession session) {
        // 获取用户
        UserVO user = (UserVO) session.getAttribute("user");
        Sort sort = new Sort(Sort.Direction.DESC, "offerDate", "onBoardDate", "paymentDate", "actualPaymentDate");
        Pageable pageable = new PageRequest(request.getCurrentPage() - 1, request.getPageSize(), sort);
        Specification<SuccessfulPerm> specification = new Specification<SuccessfulPerm>() {
            @Override
            public Predicate toPredicate(Root<SuccessfulPerm> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                SuccessfulPermVOPageSearchRequest search = request.getSearch();
                if (!StringUtils.isEmpty(search.getChannel())) {
                    Path<String> path = root.get("channel");
                    Predicate like = criteriaBuilder.like(path, "%" + search.getChannel() + "%");
                    list.add(criteriaBuilder.and(like));
                }
                if (null != search.getClientId()) {
                    Path<String> path = root.get("clientId");
                    Predicate equal = criteriaBuilder.equal(path, search.getClientId());
                    list.add(criteriaBuilder.and(equal));
                }
                if (null != search.getHrId()) {
                    Path<String> path = root.get("hrId");
                    Predicate equal = criteriaBuilder.equal(path, search.getHrId());
                    list.add(criteriaBuilder.and(equal));
                }
                if (null != search.getBilling()) {
                    Path<String> path = root.get("billing");
                    Predicate equal = criteriaBuilder.equal(path, search.getBilling());
                    list.add(criteriaBuilder.and(equal));
                }
                if (null != search.getConsultantId()) {
                    Path<String> path = root.get("consultantId");
                    Predicate equal = criteriaBuilder.equal(path, search.getConsultantId());
                    Path<String> path2 = root.get("consultantId2");
                    Predicate equal2 = criteriaBuilder.equal(path2, search.getConsultantId());
                    Path<String> path3 = root.get("consultantId3");
                    Predicate equal3 = criteriaBuilder.equal(path3, search.getConsultantId());
                    Path<String> path4 = root.get("consultantId4");
                    Predicate equal4 = criteriaBuilder.equal(path4, search.getConsultantId());
                    Path<String> path5 = root.get("consultantId5");
                    Predicate equal5 = criteriaBuilder.equal(path5, search.getConsultantId());
                    Path<String> path6 = root.get("consultantId6");
                    Predicate equal6 = criteriaBuilder.equal(path6, search.getConsultantId());
                    Path<String> path7 = root.get("consultantId7");
                    Predicate equal7 = criteriaBuilder.equal(path7, search.getConsultantId());
                    Path<String> path8 = root.get("consultantId8");
                    Predicate equal8 = criteriaBuilder.equal(path8, search.getConsultantId());
                    Path<String> path9 = root.get("consultantId9");
                    Predicate equal9 = criteriaBuilder.equal(path9, search.getConsultantId());
                    Path<String> path10 = root.get("consultantId10");
                    Predicate equal10 = criteriaBuilder.equal(path10, search.getConsultantId());
                    Path<String> path11 = root.get("consultantId11");
                    Predicate equal11 = criteriaBuilder.equal(path11, search.getConsultantId());
                    Path<String> path12 = root.get("consultantId12");
                    Predicate equal12 = criteriaBuilder.equal(path12, search.getConsultantId());
                    Path<String> path13 = root.get("consultantId13");
                    Predicate equal13 = criteriaBuilder.equal(path13, search.getConsultantId());
                    Path<String> path14 = root.get("consultantId14");
                    Predicate equal14 = criteriaBuilder.equal(path14, search.getConsultantId());
                    Path<String> path15 = root.get("consultantId15");
                    Predicate equal15 = criteriaBuilder.equal(path15, search.getConsultantId());
                    Path<String> path16 = root.get("consultantId16");
                    Predicate equal16 = criteriaBuilder.equal(path16, search.getConsultantId());
                    Path<String> path17 = root.get("consultantId17");
                    Predicate equal17 = criteriaBuilder.equal(path17, search.getConsultantId());
                    Path<String> path18 = root.get("consultantId18");
                    Predicate equal18 = criteriaBuilder.equal(path18, search.getConsultantId());
                    Path<String> path19 = root.get("consultantId19");
                    Predicate equal19 = criteriaBuilder.equal(path19, search.getConsultantId());
                    Path<String> path20 = root.get("consultantId20");
                    Predicate equal20 = criteriaBuilder.equal(path20, search.getConsultantId());
                    list.add(criteriaBuilder.or(equal, equal2, equal3, equal4, equal5
                            , equal6, equal7, equal8, equal9, equal10
                            , equal11, equal12, equal13, equal14, equal15
                            , equal16, equal17, equal18, equal19, equal20));
                }
                if (null != search.getCwId()) {
                    Path<String> path = root.get("cwId");
                    Predicate equal = criteriaBuilder.equal(path, search.getCwId());
                    list.add(criteriaBuilder.and(equal));
                }
                if (null != search.getBdId()) {
                    Path<String> path = root.get("bdId");
                    Predicate equal = criteriaBuilder.equal(path, search.getBdId());
                    list.add(criteriaBuilder.and(equal));
                }
                if (!StringUtils.isEmpty(search.getApproveStatus())) {
                    Path<String> path = root.get("approveStatus");
                    Predicate equal = criteriaBuilder.equal(path, search.getApproveStatus());
                    list.add(criteriaBuilder.and(equal));
                }
                if (!StringUtils.isEmpty(search.getCandidateChineseName())) {
                    Path<String> path = root.get("candidateChineseName");
                    Predicate equal = criteriaBuilder.like(path, "%" + search.getCandidateChineseName() + "%");
                    list.add(criteriaBuilder.and(equal));
                }
                if (null != search.getOnBoardDateStart() || null != search.getOnBoardDateEnd()) {
                    Date start = null != search.getOnBoardDateStart() ? search.getOnBoardDateStart() : DateTimeUtil.startDate;
                    Date end = null != search.getOnBoardDateEnd() ? search.getOnBoardDateEnd() : DateTimeUtil.endDate;
                    Path<Date> startPath = root.get("onBoardDate");
                    Predicate startEqual = criteriaBuilder.greaterThanOrEqualTo(startPath, start);
                    list.add(criteriaBuilder.and(startEqual));
                    Path<Date> endPath = root.get("onBoardDate");
                    Predicate endEqual = criteriaBuilder.lessThanOrEqualTo(endPath, end);
                    list.add(criteriaBuilder.and(endEqual));
                }
                if (null != search.getPaymentDateStart() || null != search.getPaymentDateEnd()) {
                    Date start = null != search.getPaymentDateStart() ? search.getPaymentDateStart() : DateTimeUtil.startDate;
                    Date end = null != search.getPaymentDateEnd() ? search.getPaymentDateEnd() : DateTimeUtil.endDate;
                    Path<Date> startPath = root.get("paymentDate");
                    Predicate startEqual = criteriaBuilder.greaterThanOrEqualTo(startPath, start);
                    list.add(criteriaBuilder.and(startEqual));
                    Path<Date> endPath = root.get("paymentDate");
                    Predicate endEqual = criteriaBuilder.lessThanOrEqualTo(endPath, end);
                    list.add(criteriaBuilder.and(endEqual));
                }
                if (null != search.getActualPaymentDateStart() || null != search.getActualPaymentDateEnd()) {
                    Date start = null != search.getActualPaymentDateStart() ? search.getActualPaymentDateStart() : DateTimeUtil.startDate;
                    Date end = null != search.getActualPaymentDateEnd() ? search.getActualPaymentDateEnd() : DateTimeUtil.endDate;
                    Path<Date> startPath = root.get("actualPaymentDate");
                    Predicate startEqual = criteriaBuilder.greaterThanOrEqualTo(startPath, start);
                    list.add(criteriaBuilder.and(startEqual));
                    Path<Date> endPath = root.get("actualPaymentDate");
                    Predicate endEqual = criteriaBuilder.lessThanOrEqualTo(endPath, end);
                    list.add(criteriaBuilder.and(endEqual));
                }
                if (null != search.getOfferDateStart() || null != search.getOfferDateEnd()) {
                    Date start = null != search.getOfferDateStart() ? search.getOfferDateStart() : DateTimeUtil.startDate;
                    Date end = null != search.getOfferDateEnd() ? search.getOfferDateEnd() : DateTimeUtil.endDate;
                    Path<Date> startPath = root.get("offerDate");
                    Predicate startEqual = criteriaBuilder.greaterThanOrEqualTo(startPath, start);
                    list.add(criteriaBuilder.and(startEqual));
                    Path<Date> endPath = root.get("offerDate");
                    Predicate endEqual = criteriaBuilder.lessThanOrEqualTo(endPath, end);
                    list.add(criteriaBuilder.and(endEqual));
                }
                if (null != search.getInvoiceDateStart() || null != search.getInvoiceDateEnd()) {
                    Date start = null != search.getInvoiceDateStart() ? search.getInvoiceDateStart() : DateTimeUtil.startDate;
                    Date end = null != search.getInvoiceDateEnd() ? search.getInvoiceDateEnd() : DateTimeUtil.endDate;
                    Path<Date> startPath = root.get("invoiceDate");
                    Predicate startEqual = criteriaBuilder.greaterThanOrEqualTo(startPath, start);
                    list.add(criteriaBuilder.and(startEqual));
                    Path<Date> endPath = root.get("invoiceDate");
                    Predicate endEqual = criteriaBuilder.lessThanOrEqualTo(endPath, end);
                    list.add(criteriaBuilder.and(endEqual));
                }
                if (null != search.getCommissionDateStart() || null != search.getCommissionDateEnd()) {
                    Date start = null != search.getCommissionDateStart() ? search.getCommissionDateStart() : DateTimeUtil.startDate;
                    Date end = null != search.getCommissionDateEnd() ? search.getCommissionDateEnd() : DateTimeUtil.endDate;
                    Path<Date> startPath = root.get("commissionDate");
                    Predicate startEqual = criteriaBuilder.greaterThanOrEqualTo(startPath, start);
                    list.add(criteriaBuilder.and(startEqual));
                    Path<Date> endPath = root.get("commissionDate");
                    Predicate endEqual = criteriaBuilder.lessThanOrEqualTo(endPath, end);
                    list.add(criteriaBuilder.and(endEqual));
                }
                if (Strings.isNotBlank(search.getType())) {
                    Path<String> path = root.get("type");
                    Predicate equal = criteriaBuilder.equal(path, search.getType());
                    list.add(criteriaBuilder.and(equal));
                }
                // 非管理员只能查询与自己相关的成功case
                if (!user.getRoles().contains(RoleEnum.ADMIN) && !user.getRoles().contains(RoleEnum.ADMIN_COMPANY)) {
                    Path<String> pathbd = root.get("bdId");
                    Predicate equalbd = criteriaBuilder.equal(pathbd, user.getId());
                    Path<String> pathcw = root.get("cwId");
                    Predicate equalcw = criteriaBuilder.equal(pathcw, user.getId());
                    Path<String> path = root.get("consultantId");
                    Predicate equal = criteriaBuilder.equal(path, user.getId());
                    Path<String> path2 = root.get("consultantId2");
                    Predicate equal2 = criteriaBuilder.equal(path2, user.getId());
                    Path<String> path3 = root.get("consultantId3");
                    Predicate equal3 = criteriaBuilder.equal(path3, user.getId());
                    Path<String> path4 = root.get("consultantId4");
                    Predicate equal4 = criteriaBuilder.equal(path4, user.getId());
                    Path<String> path5 = root.get("consultantId5");
                    Predicate equal5 = criteriaBuilder.equal(path5, user.getId());
                    Path<String> path6 = root.get("consultantId6");
                    Predicate equal6 = criteriaBuilder.equal(path6, user.getId());
                    Path<String> path7 = root.get("consultantId7");
                    Predicate equal7 = criteriaBuilder.equal(path7, user.getId());
                    Path<String> path8 = root.get("consultantId8");
                    Predicate equal8 = criteriaBuilder.equal(path8, user.getId());
                    Path<String> path9 = root.get("consultantId9");
                    Predicate equal9 = criteriaBuilder.equal(path9, user.getId());
                    Path<String> path10 = root.get("consultantId10");
                    Predicate equal10 = criteriaBuilder.equal(path10, user.getId());
                    Path<String> path11 = root.get("consultantId11");
                    Predicate equal11 = criteriaBuilder.equal(path11, user.getId());
                    Path<String> path12 = root.get("consultantId12");
                    Predicate equal12 = criteriaBuilder.equal(path12, user.getId());
                    Path<String> path13 = root.get("consultantId13");
                    Predicate equal13 = criteriaBuilder.equal(path13, user.getId());
                    Path<String> path14 = root.get("consultantId14");
                    Predicate equal14 = criteriaBuilder.equal(path14, user.getId());
                    Path<String> path15 = root.get("consultantId15");
                    Predicate equal15 = criteriaBuilder.equal(path15, user.getId());
                    Path<String> path16 = root.get("consultantId16");
                    Predicate equal16 = criteriaBuilder.equal(path16, user.getId());
                    Path<String> path17 = root.get("consultantId17");
                    Predicate equal17 = criteriaBuilder.equal(path17, user.getId());
                    Path<String> path18 = root.get("consultantId18");
                    Predicate equal18 = criteriaBuilder.equal(path18, user.getId());
                    Path<String> path19 = root.get("consultantId19");
                    Predicate equal19 = criteriaBuilder.equal(path19, user.getId());
                    Path<String> path20 = root.get("consultantId20");
                    Predicate equal20 = criteriaBuilder.equal(path20, user.getId());
                    list.add(criteriaBuilder.or(equalbd, equalcw, equal, equal2, equal3, equal4, equal5
                            , equal6, equal7, equal8, equal9, equal10
                            , equal11, equal12, equal13, equal14, equal15
                            , equal16, equal17, equal18, equal19, equal20));
                }
                // 到期未付款
                if (Optional.ofNullable(search).map(x -> x.getNonPaymentDue()).orElse(false)) {
                    // paymentDate小于等于今天，actualPaymentDate是空
//                    Date start = null != search.getPaymentDateStart() ? search.getPaymentDateStart() : DateTimeUtil.startDate;
//                    Date end = null != search.getPaymentDateEnd() ? search.getPaymentDateEnd() : DateTimeUtil.endDate;
//                    Path<Date> startPath = root.get("paymentDate");
//                    Predicate startEqual = criteriaBuilder.greaterThanOrEqualTo(startPath, start);
//                    list.add(criteriaBuilder.and(startEqual));
                    // paymentDate小于等于今天
                    Path<Date> paymentDatePath = root.get("paymentDate");
                    Predicate paymentDatePathPredicate = criteriaBuilder.lessThanOrEqualTo(paymentDatePath, new Date());
                    list.add(criteriaBuilder.and(paymentDatePathPredicate));
                    // 且actualPaymentDate是空
                    Path<Date> actualPaymentDatePath = root.get("actualPaymentDate");
                    Predicate actualPaymentDatePredicate = criteriaBuilder.isNull(actualPaymentDatePath);
                    list.add(criteriaBuilder.and(actualPaymentDatePredicate));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        Page<SuccessfulPerm> all = successfulPermRepository.findAll(specification, pageable);
        Page<SuccessfulPermVO> map = all.map(x -> TransferUtil.transferTo(x, SuccessfulPermVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                all.getTotalElements());
        return map;
    }

    /**
     * 查询统计
     *
     * @return
     */
    public SuccessfulCaseStatisticsResponse queryStatistics(SuccessfulPermVOPageRequest request, HttpSession session) {
        SuccessfulCaseStatisticsResponse response = new SuccessfulCaseStatisticsResponse();
        Page<SuccessfulPermVO> successfulPermVOPage = queryPage(request, session);
        List<SuccessfulPermVO> successfulPermVOList = Optional.ofNullable(successfulPermVOPage).map(p -> p.getContent()).orElse(Lists.emptyList());
        successfulPermVOList.stream().forEach(s -> {
            BigDecimal billing = s.getBilling();
            if (null != billing) {
                response.setBillingSum(response.getBillingSum().add(billing));
            }
            BigDecimal gp = s.getGp();
            if (null != gp) {
                response.setGpSum(response.getGpSum().add(gp));
            }
        });
        if (successfulPermVOList.size() > 0) {
            response.setBillingAvg(response.getBillingSum().divide(new BigDecimal(successfulPermVOList.size()), 2, RoundingMode.HALF_DOWN));
            response.setGpAvg(response.getGpSum().divide(new BigDecimal(successfulPermVOList.size()), 2, RoundingMode.HALF_DOWN));
        }
        return response;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    public String deleteById(Integer id) {
        Optional<SuccessfulPerm> optional = successfulPermRepository.findById(id);
        if (optional.isPresent()) {
            successfulPermRepository.deleteById(id);
            return "";
        } else {
            return "信息不存在！";
        }
    }
}

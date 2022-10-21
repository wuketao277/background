package com.hello.background.service;

import com.hello.background.domain.SuccessfulPerm;
import com.hello.background.repository.SuccessfulPermRepository;
import com.hello.background.utils.DateTimeUtil;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.SuccessfulCaseStatisticsResponse;
import com.hello.background.vo.SuccessfulPermVO;
import com.hello.background.vo.SuccessfulPermVOPageRequest;
import com.hello.background.vo.SuccessfulPermVOPageSearchRequest;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
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
    public Page<SuccessfulPermVO> queryPage(SuccessfulPermVOPageRequest request) {
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
                    list.add(criteriaBuilder.or(equal, equal2, equal3, equal4, equal5));
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
                    Predicate equal = criteriaBuilder.equal(path, search.getCandidateChineseName());
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
    public SuccessfulCaseStatisticsResponse queryStatistics(SuccessfulPermVOPageRequest request) {
        SuccessfulCaseStatisticsResponse response = new SuccessfulCaseStatisticsResponse();
        Page<SuccessfulPermVO> successfulPermVOPage = queryPage(request);
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

package com.hello.background.service;

import com.hello.background.constant.RoleEnum;
import com.hello.background.domain.Candidate;
import com.hello.background.domain.SuccessfulPerm;
import com.hello.background.domain.User;
import com.hello.background.repository.CandidateRepository;
import com.hello.background.repository.SuccessfulPermRepository;
import com.hello.background.repository.UserRepository;
import com.hello.background.utils.DateTimeUtil;
import com.hello.background.utils.EasyExcelUtil;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.*;
import org.apache.logging.log4j.util.Strings;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private UserRepository userRepository;

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
     * 下载成功case
     *
     * @return
     */
    public void downloadSuccessfulCase(SuccessfulPermVOPageRequest request, HttpSession session,
                                       HttpServletResponse response) {
        Page<SuccessfulPermVO> successfulPermVOList = queryPage(request, session);
        // 封装返回response
        EasyExcelUtil.downloadExcel(response, "成功case", null, successfulPermVOList.getContent(), SuccessfulPermVO.class);
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
                if (null != search.getClientName()) {
                    if (search.getClientName().equals("理想-集团")) {
                        list.add(criteriaBuilder.like(root.get("clientName"), "%理想%"));
                    } else if (search.getClientName().equals("一汽-集团")) {
                        list.add(criteriaBuilder.like(root.get("clientName"), "%一汽%"));
                    } else if (search.getClientName().equals("宝马-集团")) {
                        list.add(criteriaBuilder.or(
                                criteriaBuilder.like(root.get("clientName"), "%宝马%")
                                , criteriaBuilder.like(root.get("clientName"), "%领悦%")));
                    } else if (search.getClientName().equals("沃尔沃-集团")) {
                        list.add(criteriaBuilder.or(
                                criteriaBuilder.like(root.get("clientName"), "%沃尔沃%")
                                , criteriaBuilder.equal(root.get("clientName"), "亚欧汽车制造（台州）有限公司")));
                    } else {
                        list.add(criteriaBuilder.equal(root.get("clientName"), search.getClientName()));
                    }
                }
                if (Strings.isNotBlank(search.getTitle())) {
                    Path<String> path = root.get("title");
                    Predicate like = criteriaBuilder.like(path, "%" + search.getTitle() + "%");
                    list.add(criteriaBuilder.and(like));
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
                if (null != search.getLeaderId()) {
                    Path<String> path = root.get("leaderId");
                    Predicate equal = criteriaBuilder.equal(path, search.getLeaderId());
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
                    Path<String> pathLeaderId = root.get("leaderId");
                    Predicate equalLeaderId = criteriaBuilder.equal(pathLeaderId, user.getId());
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
                    list.add(criteriaBuilder.or(equalbd, equalcw, equalLeaderId, equal, equal2, equal3, equal4, equal5
                            , equal6, equal7, equal8, equal9, equal10
                            , equal11, equal12, equal13, equal14, equal15
                            , equal16, equal17, equal18, equal19, equal20));
                }
                // 到期未付款
                if (Optional.ofNullable(search).map(x -> x.getNonPaymentDue()).orElse(false)) {
                    // paymentDate小于等于今天
                    Path<Date> paymentDatePath = root.get("paymentDate");
                    Predicate paymentDatePathPredicate = criteriaBuilder.lessThanOrEqualTo(paymentDatePath, DateTimeUtil.getToday000());
                    list.add(criteriaBuilder.and(paymentDatePathPredicate));
                    // 且actualPaymentDate是空
                    Path<Date> actualPaymentDatePath = root.get("actualPaymentDate");
                    Predicate actualPaymentDatePredicate = criteriaBuilder.isNull(actualPaymentDatePath);
                    list.add(criteriaBuilder.and(actualPaymentDatePredicate));
                }
                // 一汽以外的到期未付款
                if (Optional.ofNullable(search).map(x -> x.getNonPaymentDueExcludeYiQi()).orElse(false)) {
                    // paymentDate小于等于今天
                    Path<Date> paymentDatePath = root.get("paymentDate");
                    Predicate paymentDatePathPredicate = criteriaBuilder.lessThanOrEqualTo(paymentDatePath, DateTimeUtil.getToday000());
                    list.add(criteriaBuilder.and(paymentDatePathPredicate));
                    // 且actualPaymentDate是空
                    Path<Date> actualPaymentDatePath = root.get("actualPaymentDate");
                    Predicate actualPaymentDatePredicate = criteriaBuilder.isNull(actualPaymentDatePath);
                    list.add(criteriaBuilder.and(actualPaymentDatePredicate));
                    // 排除一汽
                    List<Integer> yiqiList = new ArrayList<>();
                    yiqiList.add(83128);
                    yiqiList.add(116400);
                    yiqiList.add(506794);
                    yiqiList.add(530067);
                    yiqiList.add(534813);
                    yiqiList.add(536829);
                    yiqiList.add(551408);
                    yiqiList.add(554065);
                    yiqiList.add(561173);
                    yiqiList.add(562083);
                    yiqiList.add(565134);
                    yiqiList.add(565142);
                    yiqiList.add(567055);
                    yiqiList.add(567056);
                    yiqiList.add(567118);
                    yiqiList.add(567119);
                    yiqiList.add(567119);
                    yiqiList.add(599905);
                    Predicate clientIdPredicate = root.get("clientId").in(yiqiList);
                    list.add(criteriaBuilder.not(clientIdPredicate));
                }
                // 还在保证期的
                if (Optional.ofNullable(search).map(x -> x.getGuaranteePeriod()).orElse(false)) {
                    // guaranteeDate大于等于今天
                    Path<Date> guaranteeDatePath = root.get("guaranteeDate");
                    Predicate guaranteeDatePathPredicate = criteriaBuilder.greaterThanOrEqualTo(guaranteeDatePath, DateTimeUtil.getToday000());
                    list.add(criteriaBuilder.and(guaranteeDatePathPredicate));
                }
                // 还未入职的
                if (Optional.ofNullable(search).map(x -> x.getNonOnboard()).orElse(false)) {
                    // onBoardDate大于等于今天
                    Path<Date> onBoardDatePath = root.get("onBoardDate");
                    Predicate onBoardDatePathPredicate = criteriaBuilder.greaterThanOrEqualTo(onBoardDatePath, DateTimeUtil.getToday000());
                    list.add(criteriaBuilder.and(onBoardDatePathPredicate));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        // 从数据库中查询数据
        Page<SuccessfulPerm> all = successfulPermRepository.findAll(specification, pageable);
        // 进行数据转换
        Page<SuccessfulPermVO> map = new PageImpl<>(all.getContent().parallelStream().map(x -> {
            SuccessfulPermVO successfulPermVO = TransferUtil.transferTo(x, SuccessfulPermVO.class);
            if (null != x.getCandidateId()) {
                // 获取候选人信息
                Optional<Candidate> optionalCandidate = candidateRepository.findById(x.getCandidateId());
                // 设置候选人性别
                if (optionalCandidate.isPresent() && null != optionalCandidate.get().getGender()) {
                    successfulPermVO.setGender(optionalCandidate.get().getGender().getDescribe());
                }
            }
            return successfulPermVO;
        }).collect(Collectors.toList()),
                new PageRequest(all.getPageable().getPageNumber(), all.getPageable().getPageSize()),
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

    /**
     * 当日入职列表
     *
     * @return
     */
    public List<TodayOnboardInfoVO> todayOnboardList() {
        LocalDateTime startLDT = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 0, 0, 0);
        LocalDateTime endLDT = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 23, 59, 59);
        // 查询当天入职的猎头岗位
        List<SuccessfulPerm> byOnBoardDate = successfulPermRepository.findByTypeAndOnBoardDateBetween("perm", Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(startLDT), Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(endLDT));
        return byOnBoardDate.stream().map(s -> new TodayOnboardInfoVO(removeDimission(s))).collect(Collectors.toList());
    }

    /**
     * 移除离职员工
     *
     * @param s
     * @return
     */
    private SuccessfulPerm removeDimission(SuccessfulPerm s) {
        List<User> userList = userRepository.findAll();
        User user = null;
        // 检查CW是否离职，离职就清空顾问信息
        if (Strings.isNotBlank(s.getCwUserName())) {
            user = userList.stream().filter(u -> u.getUsername().equals(s.getCwUserName())).findFirst().get();
            if (null != user.getDimissionDate()) {
                // 对离职的员工进行清除
                s.setCwUserName(null);
            }
        }
        // 检查Leader是否离职，离职就清空顾问信息
        if (Strings.isNotBlank(s.getLeaderUserName())) {
            user = userList.stream().filter(u -> u.getUsername().equals(s.getLeaderUserName())).findFirst().get();
            if (null != user.getDimissionDate()) {
                // 对离职的员工进行清除
                s.setLeaderUserName(null);
            }
        }
        // 检查顾问是否离职，离职就清空顾问信息
        if (Strings.isNotBlank(s.getConsultantUserName())) {
            user = userList.stream().filter(u -> u.getUsername().equals(s.getConsultantUserName())).findFirst().get();
            if (null != user.getDimissionDate()) {
                // 对离职的员工进行清除
                s.setConsultantUserName(null);
            }
        }
        // 检查顾问2是否离职，离职就清空顾问信息
        if (Strings.isNotBlank(s.getConsultantUserName2())) {
            user = userList.stream().filter(u -> u.getUsername().equals(s.getConsultantUserName2())).findFirst().get();
            if (null != user.getDimissionDate()) {
                // 对离职的员工进行清除
                s.setConsultantUserName2(null);
            }
        }
        // 检查顾问3是否离职，离职就清空顾问信息
        if (Strings.isNotBlank(s.getConsultantUserName3())) {
            user = userList.stream().filter(u -> u.getUsername().equals(s.getConsultantUserName3())).findFirst().get();
            if (null != user.getDimissionDate()) {
                // 对离职的员工进行清除
                s.setConsultantUserName3(null);
            }
        }
        return s;
    }

}

package com.hello.background.service;

import com.hello.background.constant.CompanyEnum;
import com.hello.background.constant.ReimbursementApproveStatusEnum;
import com.hello.background.constant.RoleEnum;
import com.hello.background.constant.YesOrNoEnum;
import com.hello.background.domain.ReimbursementItem;
import com.hello.background.domain.ReimbursementSummary;
import com.hello.background.domain.User;
import com.hello.background.repository.ReimbursementItemRepository;
import com.hello.background.repository.ReimbursementSummaryRepository;
import com.hello.background.repository.UserRepository;
import com.hello.background.repository.UserRoleRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.ReimbursementItemVO;
import com.hello.background.vo.ReimbursementStatisticsResponse;
import com.hello.background.vo.ReimbursementSummaryVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wuketao
 * @date 2021/11/28
 * @Description
 */
@Slf4j
@Transactional
@Service
public class ReimbursementServise {

    @Autowired
    private ReimbursementItemRepository reimbursementItemRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private ReimbursementSummaryRepository reimbursementSummaryRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * 保存报销项目
     *
     * @param vo
     */
    public ReimbursementItemVO save(ReimbursementItemVO vo) {
        ReimbursementItem item = new ReimbursementItem();
        BeanUtils.copyProperties(vo, item);
        item.setCompanyName(vo.getCompany().getName());
        item = reimbursementItemRepository.save(item);
        vo.setId(item.getId());
        return vo;
    }

    /**
     * 获取查询条件中的谓词
     *
     * @param key
     * @param root
     * @param criteriaBuilder
     * @return
     */
    public Predicate getPredicateLike(String key, String value, Root<ReimbursementItem> root, CriteriaBuilder criteriaBuilder) {
        Path<String> path = root.get(key);
        Predicate predicate = criteriaBuilder.like(path, "%" + value + "%");
        return predicate;
    }

    /**
     * 获取查询条件中的谓词
     *
     * @param key
     * @param root
     * @param criteriaBuilder
     * @return
     */
    public Predicate getPredicateEqual(String key, String value, Root<ReimbursementItem> root, CriteriaBuilder criteriaBuilder) {
        Path<String> path = root.get(key);
        Predicate predicate = criteriaBuilder.equal(path, value);
        return predicate;
    }


    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param session
     * @return
     */
    public Page<ReimbursementItemVO> queryPage(Integer currentPage, Integer pageSize, String search, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        User user = userRepository.findByUsername(userVO.getUsername());
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "paymentMonth", "userName", "date");
        Specification<ReimbursementItem> specification;
        if (user.getRoles().contains(RoleEnum.ADMIN)) {
            // 管理员
            specification = new Specification<ReimbursementItem>() {
                @Override
                public Predicate toPredicate(Root<ReimbursementItem> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<>();
                    if (Strings.isNotBlank(search)) {
                        list.add(criteriaBuilder.and(criteriaBuilder.or(
                                getPredicateLike("userName", search, root, criteriaBuilder)
                                , getPredicateLike("realName", search, root, criteriaBuilder)
                                , getPredicateLike("paymentMonth", search, root, criteriaBuilder)
                                , getPredicateLike("companyName", search, root, criteriaBuilder)
                        )));
                    }
                    Predicate[] p = new Predicate[list.size()];
                    return criteriaBuilder.and(list.toArray(p));
                }
            };
        } else {
            // 普通用户
            specification = new Specification<ReimbursementItem>() {
                @Override
                public Predicate toPredicate(Root<ReimbursementItem> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<>();
                    if (Strings.isNotBlank(search)) {
                        list.add(criteriaBuilder.and(criteriaBuilder.or(
                                getPredicateLike("userName", search, root, criteriaBuilder)
                                , getPredicateLike("realName", search, root, criteriaBuilder)
                                , getPredicateLike("paymentMonth", search, root, criteriaBuilder)
                                , getPredicateLike("companyName", search, root, criteriaBuilder)
                        )));
                    }
                    // 普通用户只能查询自己的信息
                    list.add(getPredicateEqual("userName", user.getUsername(), root, criteriaBuilder));
                    Predicate[] p = new Predicate[list.size()];
                    return criteriaBuilder.and(list.toArray(p));
                }
            };
        }
        Page<ReimbursementItem> all = reimbursementItemRepository.findAll(specification, pageable);
        Page<ReimbursementItemVO> map = all.map(x -> TransferUtil.transferTo(x, ReimbursementItemVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                all.getTotalElements());
        return map;
    }

    /**
     * 查询统计
     *
     * @param currentPage
     * @param pageSize
     * @param session
     * @return
     */
    public ReimbursementStatisticsResponse queryStatistics(Integer currentPage, Integer pageSize, String search, HttpSession session) {
        ReimbursementStatisticsResponse response = new ReimbursementStatisticsResponse();
        Page<ReimbursementItemVO> reimbursementItemVOPage = queryPage(currentPage, pageSize, search, session);
        List<ReimbursementItemVO> reimbursementItemVOList = Optional.ofNullable(reimbursementItemVOPage).map(p -> p.getContent()).orElse(Lists.emptyList());
        reimbursementItemVOList.stream().forEach(r -> {
            if (null != r.getSum()) {
                // 统计总报销金额
                response.setTotalReimbursementSum(response.getTotalReimbursementSum().add(r.getSum()));
                // 统计实际需要报销的金额
                if (null != r.getNeedPay() && r.getNeedPay() == YesOrNoEnum.YES && null != r.getApproveStatus() && r.getApproveStatus() == ReimbursementApproveStatusEnum.Approved) {
                    response.setNeedReimbursementSum(response.getNeedReimbursementSum().add(r.getSum()));
                }
            }
        });
        return response;
    }

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param session
     * @return
     */
    public Page<ReimbursementSummaryVO> querySummaryPage(String search, Integer currentPage, Integer pageSize, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        User user = userRepository.findByUsername(userVO.getUsername());
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "paymentMonth", "userName");
        Specification<ReimbursementSummary> specification;
        if (user.getRoles().contains(RoleEnum.ADMIN)) {
            // 管理员
            specification = new Specification<ReimbursementSummary>() {
                @Override
                public Predicate toPredicate(Root<ReimbursementSummary> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<>();
                    if (Strings.isNotBlank(search)) {
                        list.add(criteriaBuilder.or(
                                criteriaBuilder.like(root.get("userName"), "%" + search + "%"),
                                criteriaBuilder.like(root.get("realName"), "%" + search + "%"),
                                criteriaBuilder.like(root.get("paymentMonth"), "%" + search + "%"),
                                criteriaBuilder.like(root.get("companyName"), "%" + search + "%")
                        ));
                    }
                    Predicate[] p = new Predicate[list.size()];
                    return criteriaBuilder.and(list.toArray(p));
                }
            };
        } else {
            // 普通用户
            specification = new Specification<ReimbursementSummary>() {
                @Override
                public Predicate toPredicate(Root<ReimbursementSummary> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<>();
                    if (Strings.isNotBlank(search)) {
                        list.add(criteriaBuilder.or(
                                criteriaBuilder.like(root.get("userName"), "%" + search + "%"),
                                criteriaBuilder.like(root.get("realName"), "%" + search + "%"),
                                criteriaBuilder.like(root.get("paymentMonth"), "%" + search + "%"),
                                criteriaBuilder.like(root.get("companyName"), "%" + search + "%")
                        ));
                    }
                    // 普通用户只能查询自己的信息
                    list.add(criteriaBuilder.equal(root.get("userName"), user.getUsername()));
                    Predicate[] p = new Predicate[list.size()];
                    return criteriaBuilder.and(list.toArray(p));
                }
            };
        }
        Page<ReimbursementSummary> all = reimbursementSummaryRepository.findAll(specification, pageable);
        Page<ReimbursementSummaryVO> map = all.map(x -> TransferUtil.transferTo(x, ReimbursementSummaryVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                all.getTotalElements());
        return map;
    }

    /**
     * 生成报销摘要
     */
    public void generateReimbursementSummary(String monthStr, UserVO curUser) {
        // 删除旧数据
        reimbursementSummaryRepository.deleteByPaymentMonth(monthStr);
        // 查询当月审批通过且需要报销的报销项
        List<ReimbursementItem> approveList = reimbursementItemRepository.findByPaymentMonthAndApproveStatusAndNeedPayOrderByUserId(monthStr, ReimbursementApproveStatusEnum.Approved, YesOrNoEnum.YES);
        Map<String, BigDecimal> userAndCompanyMap = new HashMap<>();
        approveList.forEach(a -> {
            // 通过登录名+公司标识作为唯一标识
            String userAndCompany = a.getUserName() + "-" + a.getCompany();
            BigDecimal bd = userAndCompanyMap.containsKey(userAndCompany) ? userAndCompanyMap.get(userAndCompany) : BigDecimal.ZERO;
            bd = bd.add(null != a.getSum() ? a.getSum() : BigDecimal.ZERO);
            userAndCompanyMap.put(userAndCompany, bd);
        });
        for (Map.Entry<String, BigDecimal> entry : userAndCompanyMap.entrySet()) {
            String[] splits = entry.getKey().split("-");
            User user = userRepository.findByUsername(splits[0]);
            ReimbursementSummary summary = new ReimbursementSummary();
            summary.setCompany(CompanyEnum.valueOf(splits[1]));
            summary.setCompanyName(CompanyEnum.valueOf(splits[1]).getName());
            summary.setPaymentMonth(monthStr);
            summary.setUserId(user.getId());
            summary.setUserName(user.getUsername());
            summary.setRealName(user.getRealname());
            summary.setSum(entry.getValue());
            summary.setUpdateTime(new Date());
            summary.setUpdateUserName(curUser.getUsername());
            reimbursementSummaryRepository.save(summary);
        }
    }

    /**
     * 获取当前月总报销金额
     *
     * @return
     */
    public Double getCurrentMonthSumReimbursement() {
        Double result = new Double(0);
        String monthStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        List<ReimbursementSummary> list = reimbursementSummaryRepository.findALLByPaymentMonth(monthStr);
        List<BigDecimal> collect = list.stream().map(r -> r.getSum()).collect(Collectors.toList());
        for (BigDecimal bd : collect) {
            if (null != bd) {
                result += bd.doubleValue();
            }
        }
        return result;
    }

    /**
     * 通过主键删除
     *
     * @param id
     */
    public void deleteById(Integer id) {
        reimbursementItemRepository.deleteById(id);
    }

    /**
     * 审批选中项
     *
     * @param reimbursementItemVOList
     */
    public void approveSelection(List<ReimbursementItemVO> reimbursementItemVOList) {
        reimbursementItemVOList.stream().forEach(r -> {
            Optional<ReimbursementItem> reimbursementItemOptional = reimbursementItemRepository.findById(r.getId());
            if (reimbursementItemOptional.isPresent()) {
                ReimbursementItem reimbursementItem = reimbursementItemOptional.get();
                reimbursementItem.setApproveStatus(ReimbursementApproveStatusEnum.Approved);
                reimbursementItemRepository.save(reimbursementItem);
            }
        });
    }
}

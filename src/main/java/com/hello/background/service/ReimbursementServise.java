package com.hello.background.service;

import com.hello.background.constant.ApproveStatusEnum;
import com.hello.background.domain.ReimbursementItem;
import com.hello.background.domain.ReimbursementSummary;
import com.hello.background.domain.User;
import com.hello.background.domain.UserRole;
import com.hello.background.repository.ReimbursementItemRepository;
import com.hello.background.repository.ReimbursementSummaryRepository;
import com.hello.background.repository.UserRepository;
import com.hello.background.repository.UserRoleRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.ReimbursementItemVO;
import com.hello.background.vo.ReimbursementSummaryVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
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
        UserVO user = (UserVO) session.getAttribute("user");
        List<UserRole> userRoleList = userRoleRepository.findByUserName(user.getUsername());
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "paymentMonth", "userName", "date");
        Specification<ReimbursementItem> specification;
        if (userRoleList.stream().anyMatch(u -> "admin".equals(u.getRoleName()))) {
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
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param session
     * @return
     */
    public Page<ReimbursementSummaryVO> querySummaryPage(Integer currentPage, Integer pageSize, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        List<UserRole> userRoleList = userRoleRepository.findByUserName(user.getUsername());
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<ReimbursementSummary> page = null;
        long total = 0;
        if (userRoleList.stream().anyMatch(u -> "admin".equals(u.getRoleName()))) {
            // 管理员
            page = reimbursementSummaryRepository.findByPaymentMonthIsNotNullOrderByPaymentMonthDescSumDescIdDesc(pageable);
            total = reimbursementSummaryRepository.count();
        } else {
            page = reimbursementSummaryRepository.findByUserNameOrderByPaymentMonthDesc(user.getUsername(), pageable);
            total = reimbursementSummaryRepository.countByUserName(user.getUsername());
        }
        Page<ReimbursementSummaryVO> map = page.map(x -> TransferUtil.transferTo(x, ReimbursementSummaryVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
        return map;
    }

    /**
     * 生成报销摘要
     */
    public void generateReimbursementSummary(UserVO curUser) {
        // 删除旧数据
        String monthStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        reimbursementSummaryRepository.deleteByPaymentMonth(monthStr);
        // 查询当月审批通过的报销项
        List<ReimbursementItem> approveList = reimbursementItemRepository.findByPaymentMonthAndApproveStatus(monthStr, ApproveStatusEnum.APPROVED.getCode());
        Map<Integer, BigDecimal> map = new HashMap<>();
        approveList.forEach(a -> {
            BigDecimal bd = map.containsKey(a.getUserId()) ? map.get(a.getUserId()) : BigDecimal.ZERO;
            bd = bd.add(null != a.getSum() ? a.getSum() : BigDecimal.ZERO);
            map.put(a.getUserId(), bd);
        });
        for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) {
            User user = userRepository.findById(entry.getKey()).get();
            ReimbursementSummary summary = new ReimbursementSummary();
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
}

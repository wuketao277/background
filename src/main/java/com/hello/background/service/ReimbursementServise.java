package com.hello.background.service;

import com.hello.background.constant.*;
import com.hello.background.domain.ReimbursementItem;
import com.hello.background.domain.ReimbursementSummary;
import com.hello.background.domain.User;
import com.hello.background.repository.ReimbursementItemRepository;
import com.hello.background.repository.ReimbursementSummaryRepository;
import com.hello.background.repository.UserRepository;
import com.hello.background.repository.UserRoleRepository;
import com.hello.background.utils.EasyExcelUtil;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
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
    public ReimbursementItemVO save(ReimbursementItemVO vo, UserVO user) {
        ReimbursementItem item = new ReimbursementItem();
        if (null == vo.getId()) {
            vo.setCreateTime(LocalDateTime.now());
            vo.setCreateUser(user.getUsername());
        }
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
    private Predicate getPredicateLike(String key, String value, Root<ReimbursementItem> root, CriteriaBuilder criteriaBuilder) {
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
    private Predicate getPredicateEqual(String key, String value, Root<ReimbursementItem> root, CriteriaBuilder criteriaBuilder) {
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
    private Page<ReimbursementItem> queryPageData(String userName, String approveStatus, String needPay, String date,
                                                  String location, String company, String paymentMonth, String type,
                                                  String kind, String invoiceNo, String sum, String description,
                                                  Integer currentPage, Integer pageSize, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        User user = userRepository.findByUsername(userVO.getUsername());
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "paymentMonth", "userName", "date");
        Specification<ReimbursementItem> specification = new Specification<ReimbursementItem>() {
            @Override
            public Predicate toPredicate(Root<ReimbursementItem> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (Strings.isNotBlank(userName)) {
                    list.add(criteriaBuilder.equal(root.get("userName"), userName));
                }
                if (Strings.isNotBlank(approveStatus)) {
                    list.add(criteriaBuilder.equal(root.get("approveStatus"), ReimbursementApproveStatusEnum.valueOf(approveStatus)));
                }
                if (Strings.isNotBlank(needPay)) {
                    list.add(criteriaBuilder.equal(root.get("needPay"), ReimbursementNeedPayEnum.valueOf(needPay)));
                }
                if (Strings.isNotBlank(date)) {
                    list.add(criteriaBuilder.equal(root.get("date"), date));
                }
                if (Strings.isNotBlank(location)) {
                    list.add(criteriaBuilder.equal(root.get("location"), ReimbursementLocationEnum.valueOf(location)));
                }
                if (Strings.isNotBlank(company)) {
                    list.add(criteriaBuilder.equal(root.get("company"), CompanyEnum.valueOf(company)));
                }
                if (Strings.isNotBlank(paymentMonth)) {
                    list.add(criteriaBuilder.equal(root.get("paymentMonth"), paymentMonth));
                }

                if (Strings.isNotBlank(type)) {
                    list.add(criteriaBuilder.equal(root.get("type"), ReimbursementTypeEnum.valueOf(type)));
                }
                if (Strings.isNotBlank(kind)) {
                    list.add(criteriaBuilder.equal(root.get("kind"), ReimbursementKindEnum.valueOf(kind)));
                }
                if (Strings.isNotBlank(invoiceNo)) {
                    list.add(criteriaBuilder.equal(root.get("invoiceNo"), invoiceNo));
                }
                if (Strings.isNotBlank(sum)) {
                    list.add(criteriaBuilder.equal(root.get("sum"), new BigDecimal(sum)));
                }
                if (Strings.isNotBlank(description)) {
                    list.add(criteriaBuilder.like(root.get("description"), "%" + description + "%"));
                }
                if (!user.getRoles().contains(RoleEnum.ADMIN) && !user.getRoles().contains(RoleEnum.ADMIN_COMPANY)) {
                    // 普通用户只能查询自己的信息
                    list.add(getPredicateEqual("userName", user.getUsername(), root, criteriaBuilder));
                } else if (!user.getRoles().contains(RoleEnum.ADMIN) && user.getRoles().contains(RoleEnum.ADMIN_COMPANY)) {
                    // 公司管理员可以查看公司所有员工数据
                    CriteriaBuilder.In<Object> consultantUserName = criteriaBuilder.in(root.get("userName"));
                    List<User> companyAllUserList = userRepository.findAllByCompany(user.getCompany());
                    for (User user : companyAllUserList) {
                        consultantUserName.value(user.getUsername());
                    }
                    list.add(consultantUserName);
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        return reimbursementItemRepository.findAll(specification, pageable);
    }

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param session
     * @return
     */
    public ReimbursementItemPageInfo queryPage(String userName, String approveStatus, String needPay, String date,
                                               String location, String company, String paymentMonth, String type,
                                               String kind, String invoiceNo, String sum, String description, Integer currentPage, Integer pageSize, HttpSession session) {
        Page<ReimbursementItem> all = queryPageData(userName, approveStatus, needPay, date, location, company, paymentMonth, type, kind, invoiceNo, sum, description, currentPage, pageSize, session);
        ReimbursementItemPageInfo pageInfo = new ReimbursementItemPageInfo();
        pageInfo.setPage(new PageImpl<>(all.getContent().stream().map(x -> TransferUtil.transferTo(x, ReimbursementItemVO.class)).collect(Collectors.toList()), new PageRequest(all.getPageable().getPageNumber(), all.getPageable().getPageSize()), all.getTotalElements()));
        all.stream().forEach(r -> {
            if (null != r.getSum()) {
                // 统计总报销金额
                pageInfo.setTotalReimbursementSum(pageInfo.getTotalReimbursementSum().add(r.getSum()));
                // 统计实际需要报销的金额
                if (null != r.getNeedPay() && r.getNeedPay() == ReimbursementNeedPayEnum.YES && null != r.getApproveStatus() && r.getApproveStatus() == ReimbursementApproveStatusEnum.Approved) {
                    pageInfo.setNeedReimbursementSum(pageInfo.getNeedReimbursementSum().add(r.getSum()));
                }
            }
        });
        pageInfo.setTotalReimbursementSum(pageInfo.getTotalReimbursementSum().setScale(2, RoundingMode.HALF_UP));
        pageInfo.setNeedReimbursementSum(pageInfo.getNeedReimbursementSum().setScale(2, RoundingMode.HALF_UP));
        return pageInfo;
    }

    /**
     * 下载报销项详情
     */
    public void downloadReimbursementItem(String userName, String approveStatus, String needPay, String date,
                                          String location, String company, String paymentMonth, String type,
                                          String kind, String invoiceNo, String sum, String description, Integer currentPage, Integer pageSize, HttpSession session, HttpServletResponse response) {
        Page<ReimbursementItem> page = queryPageData(userName, approveStatus, needPay, date, location, company, paymentMonth, type, kind, invoiceNo, sum, description, currentPage, pageSize, session);
        // 封装返回response
        try {
            EasyExcelUtil.downloadExcel(response, "报销项详情", null, page.getContent().stream().map(r -> new ReimbursementItemVODownload(r)).collect(Collectors.toList()), ReimbursementItemVODownload.class);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * 下载报销
     */
    public void downloadReimbursementSummary(String company, String userName, String paymentMonth, String
            sum, Integer currentPage, Integer pageSize, HttpSession session, HttpServletResponse response) {
        Page<ReimbursementSummary> page = querySummaryPageData(company, userName, paymentMonth, sum, currentPage, pageSize, session);
        // 封装返回response
        try {
            EasyExcelUtil.downloadExcel(response, "报销", null, page.getContent().stream().map(x -> TransferUtil.transferTo(x, ReimbursementSummaryVODownload.class)).collect(Collectors.toList()), ReimbursementSummaryVODownload.class);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * 分页查询数据
     *
     * @param currentPage
     * @param pageSize
     * @param session
     * @return
     */
    public Page<ReimbursementSummary> querySummaryPageData(String company, String userName, String
            paymentMonth, String sum, Integer currentPage, Integer pageSize, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        User user = userRepository.findByUsername(userVO.getUsername());
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "paymentMonth", "company", "userName");
        Specification<ReimbursementSummary> specification = new Specification<ReimbursementSummary>() {
            @Override
            public Predicate toPredicate(Root<ReimbursementSummary> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (Strings.isNotBlank(company)) {
                    list.add(criteriaBuilder.equal(root.get("company"), CompanyEnum.valueOf(company))
                    );
                }
                if (Strings.isNotBlank(userName)) {
                    list.add(criteriaBuilder.like(root.get("userName"), "%" + userName + "%")
                    );
                }
                if (Strings.isNotBlank(paymentMonth)) {
                    list.add(criteriaBuilder.equal(root.get("paymentMonth"), paymentMonth)
                    );
                }
                if (Strings.isNotBlank(sum)) {
                    list.add(criteriaBuilder.equal(root.get("sum"), new BigDecimal(sum.trim()))
                    );
                }
                if (!user.getRoles().contains(RoleEnum.ADMIN) && !user.getRoles().contains(RoleEnum.ADMIN_COMPANY)) {
                    // 普通用户只能查询自己的信息
                    list.add(criteriaBuilder.equal(root.get("userName"), user.getUsername()));
                } else if (!user.getRoles().contains(RoleEnum.ADMIN) && user.getRoles().contains(RoleEnum.ADMIN_COMPANY)) {
                    // 公司管理员可以查看公司所有员工数据
                    CriteriaBuilder.In<Object> consultantUserName = criteriaBuilder.in(root.get("userName"));
                    List<User> companyAllUserList = userRepository.findAllByCompany(user.getCompany());
                    for (User user : companyAllUserList) {
                        consultantUserName.value(user.getUsername());
                    }
                    list.add(consultantUserName);
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        return reimbursementSummaryRepository.findAll(specification, pageable);
    }

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param session
     * @return
     */
    public ReimbursementSummaryPageInfo querySummaryPage(String company, String userName, String
            paymentMonth, String sum, Integer currentPage, Integer pageSize, HttpSession session) {
        Page<ReimbursementSummary> all = querySummaryPageData(company, userName, paymentMonth, sum, currentPage, pageSize, session);
        ReimbursementSummaryPageInfo pageInfo = new ReimbursementSummaryPageInfo();
        pageInfo.setPage(new PageImpl<>(all.getContent().stream().map(x -> TransferUtil.transferTo(x, ReimbursementSummaryVO.class)).collect(Collectors.toList()), new PageRequest(all.getPageable().getPageNumber(), all.getPageable().getPageSize()), all.getTotalElements()));
        all.getContent().stream().forEach(r -> {
            if (null != r.getSum()) {
                pageInfo.setSum(pageInfo.getSum().add(r.getSum()));
            }
        });
        pageInfo.setSum(pageInfo.getSum().setScale(2, RoundingMode.HALF_UP));
        return pageInfo;
    }

    /**
     * 生成报销摘要
     */
    public void generateReimbursementSummary(String monthStr, UserVO curUser) {
        // 删除旧数据
        reimbursementSummaryRepository.deleteByPaymentMonth(monthStr);
        // 查询当月审批通过且需要报销的报销项
        List<ReimbursementItem> approveList = reimbursementItemRepository.findByPaymentMonthAndApproveStatusAndNeedPayOrderByUserId(monthStr, ReimbursementApproveStatusEnum.Approved, ReimbursementNeedPayEnum.YES);
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

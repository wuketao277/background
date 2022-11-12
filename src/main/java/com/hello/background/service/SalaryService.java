package com.hello.background.service;

import com.hello.background.constant.RoleEnum;
import com.hello.background.domain.Salary;
import com.hello.background.domain.SalarySpecialItem;
import com.hello.background.domain.SuccessfulPerm;
import com.hello.background.domain.User;
import com.hello.background.repository.*;
import com.hello.background.utils.DateTimeUtil;
import com.hello.background.utils.EasyExcelUtil;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 工资服务
 *
 * @author wuketao
 * @date 2021/10/15
 * @Description
 */
@Slf4j
@Transactional
@Service
public class SalaryService {
    @Autowired
    private SuccessfulPermRepository successfulPermRepository;
    @Autowired
    private SalaryRepository salaryRepository;
    @Autowired
    private SalarySpecialItemRepository salarySpecialItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    public boolean update(SalaryVO vo, UserVO user) {
        Optional<Salary> optional = salaryRepository.findById(vo.getId());
        if (!optional.isPresent()) {
            return false;
        }
        Salary salary = optional.get();
        BigDecimal finalSum = BigDecimal.ZERO;
        if (null != salary.getSum() && salary.getSum().compareTo(BigDecimal.ZERO) > 0) {
            finalSum = salary.getSum();
            if (null != vo.getPersonalTax() && vo.getPersonalTax().compareTo(BigDecimal.ZERO) > 0) {
                finalSum = finalSum.subtract(vo.getPersonalTax());
            }
            if (null != vo.getInsurance() && vo.getInsurance().compareTo(BigDecimal.ZERO) > 0) {
                finalSum = finalSum.subtract(vo.getInsurance());
            }
            if (null != vo.getGongjijin() && vo.getGongjijin().compareTo(BigDecimal.ZERO) > 0) {
                finalSum = finalSum.subtract(vo.getGongjijin());
            }
        }
        finalSum = finalSum.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        salary.setFinalSum(finalSum);
        salary.setInsurance(vo.getInsurance());
        salary.setPersonalTax(vo.getPersonalTax());
        salary.setGongjijin(vo.getGongjijin());
        salary.setWorkingDays(vo.getWorkingDays());
        salary.setDescription(vo.getDescription());
        salary.setUpdateUserName(user.getUsername());
        salary.setUpdateTime(new Date());
        salaryRepository.save(salary);
        return true;
    }

    /**
     * 生成工资
     *
     * @param month
     */
    public void generateSalary(String month, String updateUserId) {
        // 删除历史数据
        salaryRepository.deleteByMonth(month);
        LocalDate ldStartMonth = LocalDate.of(new Integer(month.substring(0, 4)), new Integer(month.substring(5, 7)), 1);
        Date start = DateTimeUtil.localDate2Date(ldStartMonth);
        LocalDate ldEndMonth = ldStartMonth.plusMonths(1);
        Date end = DateTimeUtil.localDate2Date(ldEndMonth);
        // 查询当月收款情况
        List<SuccessfulPerm> successfulPermList = successfulPermRepository.findByApproveStatusAndCommissionDateBetween("approved", start, end);
        // 查找所有当月工资特殊项
        List<SalarySpecialItem> salarySpecialItemList = salarySpecialItemRepository.findByMonth(month);
        // 查找开启状态的所有人
        List<User> userList = userRepository.findByEnabled(true);
        userList.stream().forEach(user -> {
            try {
                Salary salary = new Salary();
                salary.setConsultantId(user.getId());
                salary.setConsultantUserName(user.getUsername());
                salary.setConsultantRealName(user.getRealname());
                salary.setMonth(month);
                salary.setUpdateTime(new Date());
                salary.setUpdateUserName(updateUserId);
                // 提成金额
                BigDecimal commissionSum = BigDecimal.ZERO;
                StringBuilder sb = new StringBuilder();
                // 计算成功case的提成
                // 过滤所有和当前人有关的收款信息
                List<SuccessfulPerm> consultantSuccessfulPermList = successfulPermList.stream()
                        .filter(s -> user.getUsername().equals(s.getBdUserName())
                                || user.getUsername().equals(s.getCwUserName())
                                || user.getUsername().equals(s.getConsultantUserName())
                                || user.getUsername().equals(s.getConsultantUserName2())
                                || user.getUsername().equals(s.getConsultantUserName3())
                                || user.getUsername().equals(s.getConsultantUserName4())
                                || user.getUsername().equals(s.getConsultantUserName5())
                        )
                        .collect(Collectors.toList());
                for (SuccessfulPerm perm : consultantSuccessfulPermList) {
                    // 计算BD
                    if (user.getUsername().equals(perm.getBdUserName()) && null != perm.getBdCommissionPercent()) {
                        BigDecimal i = perm.getGp().multiply(new BigDecimal(perm.getBdCommissionPercent())).divide(new BigDecimal(100));
                        commissionSum = commissionSum.add(i);
                        sb.append(String.format("%s BD:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getBdCommissionPercent()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_DOWN), i));
                    }
                    // 计算CW
                    if (user.getUsername().equals(perm.getCwUserName()) && null != perm.getCwCommissionPercent()) {
                        BigDecimal i = perm.getGp().multiply(new BigDecimal(perm.getCwCommissionPercent())).divide(new BigDecimal(100));
                        commissionSum = commissionSum.add(i);
                        sb.append(String.format("%s CW:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getCwCommissionPercent()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_DOWN), i));
                    }
                    // 计算顾问1
                    if (user.getUsername().equals(perm.getConsultantUserName()) && null != perm.getConsultantCommissionPercent()) {
                        BigDecimal i = perm.getGp().multiply(new BigDecimal(perm.getConsultantCommissionPercent())).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_DOWN);
                        commissionSum = commissionSum.add(i);
                        sb.append(String.format("%s Consultant:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getConsultantCommissionPercent()).divide(BigDecimal.valueOf(100)), i));
                    }
                    // 计算顾问2
                    if (user.getUsername().equals(perm.getConsultantUserName2()) && null != perm.getConsultantCommissionPercent2()) {
                        BigDecimal i = perm.getGp().multiply(new BigDecimal(perm.getConsultantCommissionPercent2())).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_DOWN);
                        commissionSum = commissionSum.add(i);
                        sb.append(String.format("%s Consultant2:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getConsultantCommissionPercent2()).divide(BigDecimal.valueOf(100)), i));
                    }
                    // 计算顾问3
                    if (user.getUsername().equals(perm.getConsultantUserName3()) && null != perm.getConsultantCommissionPercent3()) {
                        BigDecimal i = perm.getGp().multiply(new BigDecimal(perm.getConsultantCommissionPercent3())).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_DOWN);
                        commissionSum = commissionSum.add(i);
                        sb.append(String.format("%s Consultant3:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getConsultantCommissionPercent3()).divide(BigDecimal.valueOf(100)), i));
                    }
                    // 计算顾问4
                    if (user.getUsername().equals(perm.getConsultantUserName4()) && null != perm.getConsultantCommissionPercent4()) {
                        BigDecimal i = perm.getGp().multiply(new BigDecimal(perm.getConsultantCommissionPercent4())).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_DOWN);
                        commissionSum = commissionSum.add(i);
                        sb.append(String.format("%s Consultant4:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getConsultantCommissionPercent4()).divide(BigDecimal.valueOf(100)), i));
                    }
                    // 计算顾问5
                    if (user.getUsername().equals(perm.getConsultantUserName5()) && null != perm.getConsultantCommissionPercent5()) {
                        BigDecimal i = perm.getGp().multiply(new BigDecimal(perm.getConsultantCommissionPercent5())).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_DOWN);
                        commissionSum = commissionSum.add(i);
                        sb.append(String.format("%s Consultant5:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getConsultantCommissionPercent5()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_DOWN), i));
                    }
                }
                sb.append("总提成：" + commissionSum + "\r\n");
                // 减去最近一个月工资的历史负债
                List<Salary> salaryList = salaryRepository.findByConsultantUserNameOrderByMonthDesc(user.getUsername());
                if (!CollectionUtils.isEmpty(salaryList) && Optional.ofNullable(salaryList.get(0)).map(s -> s.getHistoryDebt()).isPresent()) {
                    commissionSum = commissionSum.add(salaryList.get(0).getHistoryDebt());
                    sb.append("历史负债：" + salaryList.get(0).getHistoryDebt() + "\r\n");
                }
                // 当月工资特殊项中前置计算项累加到临时工资中
                List<SalarySpecialItem> salarySpecialItemListForUser = salarySpecialItemList.stream().filter(s -> user.getUsername().equals(s.getConsultantUserName()) && (Strings.isBlank(s.getIsPre()) || "yes".equals(s.getIsPre()))).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(salarySpecialItemListForUser)) {
                    for (SalarySpecialItem specialItem : salarySpecialItemListForUser) {
                        commissionSum = commissionSum.add(specialItem.getSum());
                        sb.append(String.format("前置计算工资特殊项：%s %s \r\n", specialItem.getDescription(), specialItem.getSum()));
                    }
                }
                sb.append(String.format("综合提成：%s \r\n", commissionSum));
                // 当月工资特殊项中后置计算项总和
                BigDecimal postSpecialSum = BigDecimal.ZERO;
                List<SalarySpecialItem> salarySpecialItemListForUserNotIsPre = salarySpecialItemList.stream().filter(s -> user.getUsername().equals(s.getConsultantUserName()) && "no".equals(s.getIsPre())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(salarySpecialItemListForUserNotIsPre)) {
                    for (SalarySpecialItem specialItem : salarySpecialItemListForUserNotIsPre) {
                        postSpecialSum = postSpecialSum.add(specialItem.getSum());
                        sb.append(String.format("后置计算工资特殊项：%s %s \r\n", specialItem.getDescription(), specialItem.getSum()));
                    }
                }
                if (user.getCoverbase()) {
                    // 需要cover base
                    sb.append("需要cover base" + "\r\n");
                    // 最后和底薪进行比较，取较大的值
                    BigDecimal base = null != user.getSalarybase() ? user.getSalarybase() : BigDecimal.ZERO;
                    sb.append(String.format("base:%s commission:%s 后置工资项总和:%s\r\n", base, commissionSum, postSpecialSum));
                    if (commissionSum.compareTo(base) >= 0) {
                        // 当月提成大于底薪。发提成，历史负债为0
                        BigDecimal actualSalary = commissionSum.add(postSpecialSum);
                        sb.append("commission多，按commission发：" + actualSalary + "\r\n");
                        salary.setSum(actualSalary);
                        salary.setHistoryDebt(BigDecimal.ZERO);
                    } else {
                        // 当月提成小于底薪。发底薪
                        // 实发薪资
                        BigDecimal actualSalary = base.add(postSpecialSum);
                        sb.append("base多，按base发：" + actualSalary + "\r\n");
                        salary.setSum(actualSalary);
                        // 实发薪资高于提成，历史负债为 实发薪资-底薪
                        BigDecimal newHistoryDebt = commissionSum.subtract(base);
                        sb.append(String.format("最新历史负债：%s\r\n", (newHistoryDebt)));
                        salary.setHistoryDebt(newHistoryDebt);
                    }
                } else {
                    // 不需要cover base
                    sb.append("不需要cover base" + "\r\n");
                    sb.append(String.format("base:%s commission:%s special:%s\r\n", user.getSalarybase(), commissionSum, postSpecialSum));
                    salary.setSum(user.getSalarybase().add(commissionSum).add(postSpecialSum));
                }
                sb.append("当月工资:" + salary.getSum());
                salary.setDescription(sb.toString());
                salaryRepository.save(salary);
            } catch (Exception ex) {
                log.error("generateSalary", ex);
            }
        });
    }

    /**
     * 查询分页数据
     *
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    private Page<Salary> queryPageData(HttpSession session, String loginName, String userName, String month, String pretaxIncome, String netPay, Integer currentPage, Integer pageSize) {
        // 获取用户
        UserVO user = (UserVO) session.getAttribute("user");
        // 设置分页信息
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, Sort.Direction.DESC, "month");
        // 设置查询条件
        Specification<Salary> specification = new Specification<Salary>() {
            @Override
            public Predicate toPredicate(Root<Salary> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (!user.getRoles().contains(RoleEnum.ADMIN)) {
                    // 普通用户只能查询自己的信息
                    list.add(getPredicateEqual("consultantUserName", user.getUsername(), root, criteriaBuilder));
                }
                if (Strings.isNotBlank(loginName)) {
                    list.add(criteriaBuilder.and(
                            getPredicateLike("consultantUserName", loginName, root, criteriaBuilder)
                    ));
                }
                if (Strings.isNotBlank(userName)) {
                    list.add(criteriaBuilder.and(
                            getPredicateLike("consultantRealName", userName, root, criteriaBuilder)
                    ));
                }
                if (Strings.isNotBlank(month)) {
                    list.add(criteriaBuilder.and(
                            getPredicateLike("month", month, root, criteriaBuilder)
                    ));
                }
                if (Strings.isNotBlank(pretaxIncome)) {
                    list.add(criteriaBuilder.equal(root.get("sum"), new BigDecimal(pretaxIncome.trim())));
                }
                if (Strings.isNotBlank(netPay)) {
                    list.add(criteriaBuilder.equal(root.get("finalSum"), new BigDecimal(netPay.trim())));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        return salaryRepository.findAll(specification, pageable);
    }


    /**
     * 查询分页
     *
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public SalaryInfoVO queryPage(HttpSession session, String loginName, String userName, String month, String pretaxIncome, String netPay, Integer currentPage, Integer pageSize) {
        SalaryInfoVO vo = new SalaryInfoVO();
        Page<Salary> salaryPage = queryPageData(session, loginName, userName, month, pretaxIncome, netPay, currentPage, pageSize);
        PageImpl<SalaryVO> salaryVOPage = new PageImpl<>(salaryPage.getContent().stream().map(x -> TransferUtil.transferTo(x, SalaryVO.class)).collect(Collectors.toList()),
                new PageRequest(salaryPage.getPageable().getPageNumber(), salaryPage.getPageable().getPageSize()),
                salaryPage.getTotalElements());
        vo.setPage(salaryVOPage);
        List<Salary> salaryList = salaryPage.getContent();
        for (Salary salary : salaryList) {
            if (null != salary.getSum()) {
                vo.setCurMonthPreTaxSum(vo.getCurMonthPreTaxSum().add(salary.getSum()));
            }
            if (null != salary.getFinalSum()) {
                vo.setCurMonthAfterTaxSum(vo.getCurMonthAfterTaxSum().add(salary.getFinalSum()));
            }
        }
        return vo;
    }

    /**
     * 下载薪资
     */
    public void downloadSalary(HttpSession session, HttpServletResponse response, String loginName, String userName, String month, String pretaxIncome, String netPay, Integer currentPage, Integer pageSize) {
        Page<Salary> salaryPage = queryPageData(session, loginName, userName, month, pretaxIncome, netPay, currentPage, pageSize);
        // 封装返回response
        EasyExcelUtil.downloadExcel(response, "薪资", null, salaryPage.getContent().stream().map(x -> TransferUtil.transferTo(x, SalaryVODownload.class)).collect(Collectors.toList()), SalaryVODownload.class);
    }

    /**
     * 获取查询条件中的谓词
     *
     * @param key
     * @param root
     * @param criteriaBuilder
     * @return
     */
    private Predicate getPredicateLike(String key, String value, Root<Salary> root, CriteriaBuilder criteriaBuilder) {
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
    private Predicate getPredicateEqual(String key, String value, Root<Salary> root, CriteriaBuilder criteriaBuilder) {
        Path<String> path = root.get(key);
        Predicate predicate = criteriaBuilder.equal(path, value);
        return predicate;
    }
}

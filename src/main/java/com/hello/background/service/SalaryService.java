package com.hello.background.service;

import com.hello.background.constant.*;
import com.hello.background.domain.*;
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
import java.math.RoundingMode;
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
    private CommonService commonService;
    @Autowired
    private HolidayRepository holidayRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private KPIService kpiService;

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
     * 通过ID删除
     *
     * @param id
     */
    public void deleteById(Integer id) {
        salaryRepository.deleteById(id);
    }

    /**
     * 判断用户是否有成功case
     *
     * @param successfulPermList
     * @param userName
     * @return
     */
    private boolean hasSuccessfulPerm(List<SuccessfulPerm> successfulPermList, String userName) {
        return successfulPermList.stream()
                .anyMatch(s -> userName.equals(s.getBdUserName())
                        || userName.equals(s.getCwUserName())
                        || userName.equals(s.getLeaderUserName())
                        || userName.equals(s.getConsultantUserName())
                        || userName.equals(s.getConsultantUserName2())
                        || userName.equals(s.getConsultantUserName3())
                        || userName.equals(s.getConsultantUserName4())
                        || userName.equals(s.getConsultantUserName5())
                        || userName.equals(s.getConsultantUserName6())
                        || userName.equals(s.getConsultantUserName7())
                        || userName.equals(s.getConsultantUserName8())
                        || userName.equals(s.getConsultantUserName9())
                        || userName.equals(s.getConsultantUserName10())
                        || userName.equals(s.getConsultantUserName11())
                        || userName.equals(s.getConsultantUserName12())
                        || userName.equals(s.getConsultantUserName13())
                        || userName.equals(s.getConsultantUserName14())
                        || userName.equals(s.getConsultantUserName15())
                        || userName.equals(s.getConsultantUserName16())
                        || userName.equals(s.getConsultantUserName17())
                        || userName.equals(s.getConsultantUserName18())
                        || userName.equals(s.getConsultantUserName19())
                        || userName.equals(s.getConsultantUserName20())
                );
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
        // 获取所有正常状态的账号，屏蔽入职日期晚于计算工资月的账号，屏蔽兼职账号且没有成功case
        List<User> userList = userRepository.findByEnabled(true).stream()
                .filter(x -> null != x.getOnBoardDate() && x.getOnBoardDate().compareTo(end) <= 0)
                .filter(z -> z.getJobType() != JobTypeEnum.PARTTIME || hasSuccessfulPerm(successfulPermList, z.getUsername()))
                .collect(Collectors.toList());
        // 遍历用户，计算每人的业绩
        userList.stream().forEach(user -> {
            try {
                Salary salary = new Salary();
                salary.setConsultantId(user.getId());
                salary.setConsultantUserName(user.getUsername());
                salary.setConsultantRealName(user.getRealname());
                salary.setCompanyName(user.getCompany().getName());
                salary.setCompany(user.getCompany().toString());
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
                                || user.getUsername().equals(s.getLeaderUserName())
                                || user.getUsername().equals(s.getConsultantUserName())
                                || user.getUsername().equals(s.getConsultantUserName2())
                                || user.getUsername().equals(s.getConsultantUserName3())
                                || user.getUsername().equals(s.getConsultantUserName4())
                                || user.getUsername().equals(s.getConsultantUserName5())
                                || user.getUsername().equals(s.getConsultantUserName6())
                                || user.getUsername().equals(s.getConsultantUserName7())
                                || user.getUsername().equals(s.getConsultantUserName8())
                                || user.getUsername().equals(s.getConsultantUserName9())
                                || user.getUsername().equals(s.getConsultantUserName10())
                                || user.getUsername().equals(s.getConsultantUserName11())
                                || user.getUsername().equals(s.getConsultantUserName12())
                                || user.getUsername().equals(s.getConsultantUserName13())
                                || user.getUsername().equals(s.getConsultantUserName14())
                                || user.getUsername().equals(s.getConsultantUserName15())
                                || user.getUsername().equals(s.getConsultantUserName16())
                                || user.getUsername().equals(s.getConsultantUserName17())
                                || user.getUsername().equals(s.getConsultantUserName18())
                                || user.getUsername().equals(s.getConsultantUserName19())
                                || user.getUsername().equals(s.getConsultantUserName20())
                        )
                        .collect(Collectors.toList());
                for (SuccessfulPerm perm : consultantSuccessfulPermList) {
                    // 计算BD
                    if (user.getUsername().equals(perm.getBdUserName()) && null != perm.getBdCommissionPercent()) {
                        BigDecimal i = perm.getGp().multiply(new BigDecimal(perm.getBdCommissionPercent())).divide(new BigDecimal(100), 2, RoundingMode.DOWN);
                        commissionSum = commissionSum.add(i);
                        sb.append(String.format("%s BD:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getBdCommissionPercent()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_DOWN), i));
                    }
                    // 计算CW
                    if (user.getUsername().equals(perm.getCwUserName()) && null != perm.getCwCommissionPercent()) {
                        BigDecimal i = perm.getGp().multiply(new BigDecimal(perm.getCwCommissionPercent())).divide(new BigDecimal(100), 2, RoundingMode.DOWN);
                        commissionSum = commissionSum.add(i);
                        sb.append(String.format("%s CW:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getCwCommissionPercent()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_DOWN), i));
                    }
                    // 计算Leader
                    if (user.getUsername().equals(perm.getLeaderUserName()) && null != perm.getLeaderCommissionPercent()) {
                        BigDecimal i = perm.getGp().multiply(new BigDecimal(perm.getLeaderCommissionPercent())).divide(new BigDecimal(100), 2, RoundingMode.DOWN);
                        commissionSum = commissionSum.add(i);
                        sb.append(String.format("%s Leader:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getLeaderCommissionPercent()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_DOWN), i));
                    }
                    // 计算顾问1
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName(), perm.getConsultantCommissionPercent(), user.getUsername(), sb, perm));
                    // 计算顾问2
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName2(), perm.getConsultantCommissionPercent2(), user.getUsername(), sb, perm));
                    // 计算顾问3
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName3(), perm.getConsultantCommissionPercent3(), user.getUsername(), sb, perm));
                    // 计算顾问4
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName4(), perm.getConsultantCommissionPercent4(), user.getUsername(), sb, perm));
                    // 计算顾问5
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName5(), perm.getConsultantCommissionPercent5(), user.getUsername(), sb, perm));
                    // 计算顾问6
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName6(), perm.getConsultantCommissionPercent6(), user.getUsername(), sb, perm));
                    // 计算顾问7
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName7(), perm.getConsultantCommissionPercent7(), user.getUsername(), sb, perm));
                    // 计算顾问8
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName8(), perm.getConsultantCommissionPercent8(), user.getUsername(), sb, perm));
                    // 计算顾问9
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName9(), perm.getConsultantCommissionPercent9(), user.getUsername(), sb, perm));
                    // 计算顾问10
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName10(), perm.getConsultantCommissionPercent10(), user.getUsername(), sb, perm));
                    // 计算顾问11
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName11(), perm.getConsultantCommissionPercent11(), user.getUsername(), sb, perm));
                    // 计算顾问12
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName12(), perm.getConsultantCommissionPercent12(), user.getUsername(), sb, perm));
                    // 计算顾问13
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName13(), perm.getConsultantCommissionPercent13(), user.getUsername(), sb, perm));
                    // 计算顾问14
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName14(), perm.getConsultantCommissionPercent14(), user.getUsername(), sb, perm));
                    // 计算顾问15
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName15(), perm.getConsultantCommissionPercent15(), user.getUsername(), sb, perm));
                    // 计算顾问16
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName16(), perm.getConsultantCommissionPercent16(), user.getUsername(), sb, perm));
                    // 计算顾问17
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName17(), perm.getConsultantCommissionPercent17(), user.getUsername(), sb, perm));
                    // 计算顾问18
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName18(), perm.getConsultantCommissionPercent18(), user.getUsername(), sb, perm));
                    // 计算顾问19
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName19(), perm.getConsultantCommissionPercent19(), user.getUsername(), sb, perm));
                    // 计算顾问20
                    commissionSum = commissionSum.add(calcPersonalCommission(perm.getConsultantUserName20(), perm.getConsultantCommissionPercent20(), user.getUsername(), sb, perm));
                }
                sb.append("成功case提成：" + commissionSum + "\r\n");
                // 减去最近一个月工资的起提点
                List<Salary> salaryList = salaryRepository.findByConsultantUserNameOrderByMonthDesc(user.getUsername());
                if (!CollectionUtils.isEmpty(salaryList) && Optional.ofNullable(salaryList.get(0)).map(s -> s.getHistoryDebt()).isPresent()) {
                    commissionSum = commissionSum.add(salaryList.get(0).getHistoryDebt());
                    sb.append("历史起提点：" + salaryList.get(0).getHistoryDebt() + "\r\n");
                }
                // 当月工资特殊项中 前置奖金类型的累加到奖金总和中
                List<SalarySpecialItem> salarySpecialItemListForUser = salarySpecialItemList.stream().filter(s -> user.getUsername().equals(s.getConsultantUserName()) && null != s.getType() && SalarySpecialItemTypeEnum.COMMISSION.equals(s.getType()) && Strings.isNotBlank(s.getIsPre()) && "yes".equals(s.getIsPre())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(salarySpecialItemListForUser)) {
                    for (SalarySpecialItem specialItem : salarySpecialItemListForUser) {
                        commissionSum = commissionSum.add(specialItem.getSum());
                        sb.append(String.format("前置奖金类型特殊项：%s %s \r\n", specialItem.getDescription(), specialItem.getSum()));
                    }
                }
                sb.append(String.format("综合提成：%s \r\n", commissionSum));
                // 计算工作天数，并扣除事假工资
                List<Holiday> leaveList = holidayRepository.findAllByHolidayDateBetweenAndUserNameAndApproveStatus(start, DateTimeUtil.localDate2Date(ldEndMonth.plusDays(1)), user.getUsername(), HolidayApproveStatusEnum.APPROVED);
                salary.setWorkingDays(commonService.calcWorkdaysBetween(ldStartMonth, ldEndMonth.plusDays(-1), leaveList));
                // 通过请假调整基本薪资
                BigDecimal userSalarySum = calcBaseSalary(user, sb, leaveList);
                // 当月工资特殊项中 前置工资类型的计算项总和中
                List<SalarySpecialItem> salarySpecialItemListForSalary = salarySpecialItemList.stream().filter(s -> user.getUsername().equals(s.getConsultantUserName()) && null != s.getType() && SalarySpecialItemTypeEnum.SALARY.equals(s.getType()) && Strings.isNotBlank(s.getIsPre()) && "yes".equals(s.getIsPre())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(salarySpecialItemListForSalary)) {
                    for (SalarySpecialItem specialItem : salarySpecialItemListForSalary) {
                        userSalarySum = userSalarySum.add(specialItem.getSum());
                        sb.append(String.format("前置工资类型特殊项：%s %s \r\n", specialItem.getDescription(), specialItem.getSum()));
                    }
                }
                sb.append(String.format("综合工资：%s \r\n", userSalarySum));
                // 判断是否考核kpi，从KPI历史记录中获取用户的当月数据
//                KPI kpi = kpiService.findByMonthAndUserName(month, user.getUsername());
//                if (Optional.ofNullable(kpi).map(k -> k.getCheckKPI()).orElse(user.getCheckKPI())) {
//                    sb.append("需要考核KPI" + "\r\n");
//                    BigDecimal finishRate = kpi.getFinishRate();
//                    sb.append("KPI得分" + finishRate + "\r\n");
//                    if (finishRate.compareTo(new BigDecimal(99)) < 0) {
//                        // kpi 达成率小于99
//                        if (commissionSum.compareTo(BigDecimal.ZERO) > 0) {
//                            commissionSum = commissionSum.multiply(finishRate).divide(new BigDecimal(100), 2, RoundingMode.DOWN);
//                            sb.append(String.format("综合提成*KPI达成率：%s \r\n", commissionSum));
//                        }
//                        if (userSalarySum.compareTo(BigDecimal.ZERO) > 0) {
//                            userSalarySum = userSalarySum.multiply(finishRate).divide(new BigDecimal(100), 2, RoundingMode.DOWN);
//                            sb.append(String.format("综合工资*KPI达成率：%s \r\n", userSalarySum));
//                        }
//                    } else {
//                        sb.append("KPI大于等于99%，不用折算工资。\r\n");
//                    }
//                } else {
//                    sb.append("不需要考核KPI" + "\r\n");
//                }
                // 判断是否需要cover base
                if (user.getCoverbase()) {
                    // 需要cover base
                    sb.append("需要cover base" + "\r\n");
                    // 最后和底薪进行比较，取较大的值
                    sb.append(String.format("base:%s commission:%s\r\n", userSalarySum, commissionSum));
                    if (commissionSum.compareTo(userSalarySum) >= 0) {
                        // 当月提成大于底薪。发提成，起提点为0
                        sb.append("commission多，按commission发：" + commissionSum + "\r\n");
                        salary.setSum(commissionSum);
                        salary.setHistoryDebt(BigDecimal.ZERO);
                    } else {
                        // 当月提成小于底薪。发底薪
                        // 实发薪资
                        sb.append("base多，按base发：" + userSalarySum + "\r\n");
                        salary.setSum(userSalarySum);
                        // 实发薪资高于提成，起提点为 实发薪资-底薪
                        BigDecimal newHistoryDebt = commissionSum.subtract(userSalarySum);
                        sb.append(String.format("最新起提点：%s\r\n", (newHistoryDebt)));
                        salary.setHistoryDebt(newHistoryDebt);
                    }
                } else {
                    // 不需要cover base
                    sb.append("不需要cover base" + "\r\n");
                    sb.append(String.format("base:%s commission:%s\r\n", userSalarySum, commissionSum));
                    salary.setSum(userSalarySum.add(commissionSum));
                }
                // 当月工资特殊项中 后置项累加到工资中
                salarySpecialItemList.stream().filter(s -> user.getUsername().equals(s.getConsultantUserName()) && Strings.isNotBlank(s.getIsPre()) && "no".equals(s.getIsPre())).collect(Collectors.toList()).forEach(ss -> {
                    salary.setSum(salary.getSum().add(ss.getSum()));
                    sb.append(String.format("后置特殊项：%s %s \r\n", ss.getDescription(), ss.getSum()));
                });
                sb.append("当月工资:" + salary.getSum());
                salary.setDescription(sb.toString());
                salaryRepository.save(salary);
            } catch (Exception ex) {
                log.error("generateSalary", ex);
            }
        });
    }

    /**
     * 计算基本薪资
     *
     * @param user
     * @param sb
     * @param leaveList
     * @return
     */
    private BigDecimal calcBaseSalary(User user, StringBuilder sb, List<Holiday> leaveList) {
        // 从员工薪资表中获取基本薪资
        BigDecimal userSalarySum = null != user.getSalarybase() ? user.getSalarybase() : BigDecimal.ZERO;
        // 通过请假情况，调整薪资
        if (leaveList.size() > 0) {
            // 事假
            BigDecimal shiLeaveCount = BigDecimal.ZERO;
            // 病假
            BigDecimal bingLeaveCount = BigDecimal.ZERO;
            for (Holiday holiday : leaveList) {
                if (null != holiday.getHolidayType() && HolidayTypeEnum.SHI.equals(holiday.getHolidayType())) {
                    shiLeaveCount = shiLeaveCount.add(holiday.getHolidayLong());
                }
                if (null != holiday.getHolidayType() && HolidayTypeEnum.BING.equals(holiday.getHolidayType())) {
                    bingLeaveCount = bingLeaveCount.add(holiday.getHolidayLong());
                }
            }
            if (shiLeaveCount.compareTo(BigDecimal.ZERO) > 0 || bingLeaveCount.compareTo(BigDecimal.ZERO) > 0) {
                // 员工工资折算病假、事假 基本工资-(平均工资*(事假天数+病假*0.6))
                userSalarySum = userSalarySum.subtract(userSalarySum.divide(new BigDecimal(21.75)).multiply(shiLeaveCount.add(bingLeaveCount.multiply(new BigDecimal(0.6)))));
                sb.append(String.format("员工工资折算病假、事假 %d-(%d*(%d+%d*0.6)) \r\n", userSalarySum, userSalarySum.divide(new BigDecimal(21.75)), shiLeaveCount, bingLeaveCount));
            }
        }
        return userSalarySum;
    }

    /**
     * 计算个人提成
     *
     * @return
     */
    private BigDecimal calcPersonalCommission(String consultantUserName, BigDecimal consultantCommissionPercent, String currentUserName, StringBuilder msgSB, SuccessfulPerm perm) {
        BigDecimal commission = BigDecimal.ZERO;
        // 当前用户和成功case中的顾问名称一致，且提成比例不为空
        if (currentUserName.equals(consultantUserName)
                && (null != consultantCommissionPercent && consultantCommissionPercent.compareTo(BigDecimal.ZERO) > 0)) {
            commission = perm.getGp().multiply(consultantCommissionPercent).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_DOWN);
            msgSB.append(String.format("%s %s:%s*%s=%s \r\n", perm.getCandidateChineseName(), consultantUserName, perm.getGp(), consultantCommissionPercent.divide(BigDecimal.valueOf(100), 3, BigDecimal.ROUND_HALF_DOWN), commission));
        }
        return commission;
    }

    /**
     * 查询分页数据
     *
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    private Page<Salary> queryPageData(HttpSession session, String loginName, String userName, String month, String pretaxIncome, String netPay, String company, Integer currentPage, Integer pageSize) {
        // 获取用户
        UserVO user = (UserVO) session.getAttribute("user");
        List<Sort.Order> orderList = new ArrayList<>();
        orderList.add(new Sort.Order(Sort.Direction.DESC, "month"));
        orderList.add(new Sort.Order(Sort.Direction.DESC, "companyName"));
        orderList.add(new Sort.Order(Sort.Direction.DESC, "sum"));
        orderList.add(new Sort.Order(Sort.Direction.ASC, "historyDebt"));
        orderList.add(new Sort.Order(Sort.Direction.ASC, "consultantId"));
        // 设置分页信息
        Pageable pageable = new PageRequest(currentPage - 1, pageSize, new Sort(orderList));
        // 设置查询条件
        Specification<Salary> specification = new Specification<Salary>() {
            @Override
            public Predicate toPredicate(Root<Salary> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (!user.getRoles().contains(RoleEnum.ADMIN) && !user.getRoles().contains(RoleEnum.ADMIN_COMPANY)) {
                    // 普通用户只能查询自己的信息
                    list.add(criteriaBuilder.equal(root.get("consultantUserName"), user.getUsername()));
                } else if (!user.getRoles().contains(RoleEnum.ADMIN) && user.getRoles().contains(RoleEnum.ADMIN_COMPANY)) {
                    // 不是管理员，但是公司管理员，可以查询公司的所有员工
                    CriteriaBuilder.In<Object> consultantUserName = criteriaBuilder.in(root.get("consultantUserName"));
                    List<User> companyAllUserList = userRepository.findAllByCompany(user.getCompany());
                    for (User user : companyAllUserList) {
                        consultantUserName.value(user.getUsername());
                    }
                    list.add(consultantUserName);
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
                if (Strings.isNotBlank(company)) {
                    // 通过公司来过滤
                    CriteriaBuilder.In<Object> consultantUserNameIn = criteriaBuilder.in(root.get("consultantUserName"));
                    CompanyEnum companyEnum = CompanyEnum.valueOf(company);
                    List<User> userList = userRepository.findAllByCompany(companyEnum);
                    if (userList.size() > 0) {
                        for (User user : userList) {
                            consultantUserNameIn.value(user.getUsername());
                        }
                        list.add(consultantUserNameIn);
                    }
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
    public SalaryInfoVO queryPage(HttpSession session, String loginName, String userName, String month, String pretaxIncome, String netPay, String company, Integer currentPage, Integer pageSize) {
        SalaryInfoVO vo = new SalaryInfoVO();
        Page<Salary> salaryPage = queryPageData(session, loginName, userName, month, pretaxIncome, netPay, company, currentPage, pageSize);
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
    public void downloadSalary(HttpSession session, HttpServletResponse response, String loginName, String userName, String month, String pretaxIncome, String netPay, String company, Integer currentPage, Integer pageSize) {
        Page<Salary> salaryPage = queryPageData(session, loginName, userName, month, pretaxIncome, netPay, company, currentPage, pageSize);
        // 封装返回response
        try {
            EasyExcelUtil.downloadExcel(response, "薪资", null, salaryPage.getContent().stream().map(x -> TransferUtil.transferTo(x, SalaryVODownload.class)).collect(Collectors.toList()), SalaryVODownload.class);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
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

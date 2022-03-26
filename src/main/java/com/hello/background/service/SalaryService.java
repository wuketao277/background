package com.hello.background.service;

import com.hello.background.domain.*;
import com.hello.background.repository.*;
import com.hello.background.utils.DateTimeUtil;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.SalaryVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
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
        Integer finalSum = 0;
        if (null != salary.getSum() && salary.getSum() > 0) {
            finalSum = salary.getSum();
            if (null != vo.getPersonalTax() && vo.getPersonalTax() > 0) {
                finalSum -= vo.getPersonalTax();
            }
            if (null != vo.getInsurance() && vo.getInsurance() > 0) {
                finalSum -= vo.getInsurance();
            }
            if (null != vo.getGongjijin() && vo.getGongjijin() > 0) {
                finalSum -= vo.getGongjijin();
            }
        }
        salary.setFinalSum(finalSum);
        salary.setInsurance(vo.getInsurance());
        salary.setPersonalTax(vo.getPersonalTax());
        salary.setGongjijin(vo.getGongjijin());
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
                Integer sum = 0;
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
                        int i = perm.getGp() * perm.getBdCommissionPercent() / 100;
                        sum += i;
                        sb.append(String.format("%s BD:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getBdCommissionPercent()).divide(new BigDecimal(100)), i));
                    }
                    // 计算CW
                    if (user.getUsername().equals(perm.getCwUserName()) && null != perm.getCwCommissionPercent()) {
                        int i = perm.getGp() * perm.getCwCommissionPercent() / 100;
                        sum += i;
                        sb.append(String.format("%s CW:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getCwCommissionPercent()).divide(BigDecimal.valueOf(100)), i));
                    }
                    // 计算顾问1
                    if (user.getUsername().equals(perm.getConsultantUserName()) && null != perm.getConsultantCommissionPercent()) {
                        int i = perm.getGp() * perm.getConsultantCommissionPercent() / 100;
                        sum += i;
                        sb.append(String.format("%s Consultant:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getConsultantCommissionPercent()).divide(BigDecimal.valueOf(100)), i));
                    }
                    // 计算顾问2
                    if (user.getUsername().equals(perm.getConsultantUserName2()) && null != perm.getConsultantCommissionPercent2()) {
                        int i = perm.getGp() * perm.getConsultantCommissionPercent2() / 100;
                        sum += i;
                        sb.append(String.format("%s Consultant2:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getConsultantCommissionPercent2()).divide(BigDecimal.valueOf(100)), i));
                    }
                    // 计算顾问3
                    if (user.getUsername().equals(perm.getConsultantUserName3()) && null != perm.getConsultantCommissionPercent3()) {
                        int i = perm.getGp() * perm.getConsultantCommissionPercent3() / 100;
                        sum += i;
                        sb.append(String.format("%s Consultant3:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getConsultantCommissionPercent3()).divide(BigDecimal.valueOf(100)), i));
                    }
                    // 计算顾问4
                    if (user.getUsername().equals(perm.getConsultantUserName4()) && null != perm.getConsultantCommissionPercent4()) {
                        int i = perm.getGp() * perm.getConsultantCommissionPercent4() / 100;
                        sum += i;
                        sb.append(String.format("%s Consultant4:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getConsultantCommissionPercent4()).divide(BigDecimal.valueOf(100)), i));
                    }
                    // 计算顾问5
                    if (user.getUsername().equals(perm.getConsultantUserName5()) && null != perm.getConsultantCommissionPercent5()) {
                        int i = perm.getGp() * perm.getConsultantCommissionPercent5() / 100;
                        sum += i;
                        sb.append(String.format("%s Consultant5:%s*%s=%s \r\n", perm.getCandidateChineseName(), perm.getGp(), BigDecimal.valueOf(perm.getConsultantCommissionPercent5()).divide(BigDecimal.valueOf(100)), i));
                    }
                }
                // 减去最近一个月工资的历史负债
                List<Salary> salaryList = salaryRepository.findByConsultantUserNameOrderByMonthDesc(user.getUsername());
                if (!CollectionUtils.isEmpty(salaryList) && Optional.ofNullable(salaryList.get(0)).map(s -> s.getHistoryDebt()).isPresent()) {
                    sum += salaryList.get(0).getHistoryDebt();
                    sb.append("历史负债：" + salaryList.get(0).getHistoryDebt() + "\r\n");
                }
                // 累加当月工资特殊项
                List<SalarySpecialItem> salarySpecialItemListForUser = salarySpecialItemList.stream().filter(s -> user.getUsername().equals(s.getConsultantUserName())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(salarySpecialItemListForUser)) {
                    for (SalarySpecialItem specialItem : salarySpecialItemListForUser) {
                        sum += specialItem.getSum();
                        sb.append(String.format("工资特殊项：%s %s \r\n", specialItem.getDescription(), specialItem.getSum()));
                    }
                }
                sb.append(String.format("总提成：%s \r\n", sum));
                if (user.getCoverbase()) {
                    // 需要cover base
                    sb.append("需要cover base" + "\r\n");
                    // 最后和底薪进行比较，取较大的值
                    Integer base = null != user.getSalarybase() ? user.getSalarybase() : 0;
                    sb.append(String.format("base:%s commission:%s \r\n", base, sum));
                    if (sum >= base) {
                        // 当月提成大于底薪。发提成，历史负债为0
                        sb.append("commission多发commission：" + sum + "\r\n");
                        salary.setSum(sum);
                        salary.setHistoryDebt(0);
                    } else {
                        // 当月提成小于底薪。发底薪
                        sb.append("base多发base：" + base + "\r\n");
                        salary.setSum(base);
                        // 需要cover base的，历史负债为提成-底薪
                        sb.append(String.format("最新历史负债：%s\r\n", (sum - base)));
                        salary.setHistoryDebt(sum - base);
                    }
                } else {
                    // 不需要cover base
                    sb.append("不需要cover base" + "\r\n");
                    sb.append(String.format("base:%s commission:%s \r\n", user.getSalarybase(), sum));
                    salary.setSum(sum + user.getSalarybase());
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
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<SalaryVO> queryPage(HttpSession session, String search, Integer currentPage, Integer pageSize) {
        UserVO user = (UserVO) session.getAttribute("user");
        List<UserRole> userRoleList = userRoleRepository.findByUserName(user.getUsername());
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<Salary> salaryPage;
        long total = 0;
        if (userRoleList.stream().anyMatch(u -> "admin".equals(u.getRoleName()))) {
            // 管理员
            search = "%" + search + "%";
            salaryPage = salaryRepository.findByConsultantRealNameLikeOrConsultantUserNameLikeOrMonthLikeOrderByMonthDescSumDescHistoryDebtAsc(search, search, search, pageable);
            total = salaryRepository.countByConsultantRealNameLikeOrConsultantUserNameLikeOrMonthLike(search, search, search);
        } else {
            // 非管理员，只能查询自己的工资信息
            salaryPage = salaryRepository.findByConsultantUserNameOrderByMonthDesc(user.getUsername(), pageable);
            total = salaryRepository.countByConsultantUserName(user.getUsername());
        }
        Page<SalaryVO> map = salaryPage.map(x -> TransferUtil.transferTo(x, SalaryVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
        return map;
    }
}

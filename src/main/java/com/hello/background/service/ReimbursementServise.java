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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param session
     * @return
     */
    public Page<ReimbursementItemVO> queryPage(Integer currentPage, Integer pageSize, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        List<UserRole> userRoleList = userRoleRepository.findByUserName(user.getUsername());
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<ReimbursementItem> page = null;
        long total = 0;
        if (userRoleList.stream().anyMatch(u -> "admin".equals(u.getRoleName()))) {
            // 管理员
            page = reimbursementItemRepository.findByUpdateTimeIsNotNullOrderByUpdateTimeDesc(pageable);
            total = reimbursementItemRepository.count();
        } else {
            page = reimbursementItemRepository.findByUserNameOrderByUpdateTimeDesc(user.getUsername(), pageable);
            total = reimbursementItemRepository.countByUserName(user.getUsername());
        }
        Page<ReimbursementItemVO> map = page.map(x -> TransferUtil.transferTo(x, ReimbursementItemVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
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
}

package com.hello.background.service;

import com.hello.background.domain.ReimbursementItem;
import com.hello.background.domain.ReimbursementSummary;
import com.hello.background.domain.UserRole;
import com.hello.background.repository.ReimbursementItemRepository;
import com.hello.background.repository.ReimbursementSummaryRepository;
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
import java.util.List;

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
            page = reimbursementSummaryRepository.findByPaymentMonthIsNotNullOrderByUpdateTimeDesc(pageable);
            total = reimbursementSummaryRepository.count();
        } else {
            page = reimbursementSummaryRepository.findByUserNameOrderByUpdateTimeDesc(user.getUsername(), pageable);
            total = reimbursementSummaryRepository.countByUserName(user.getUsername());
        }
        Page<ReimbursementSummaryVO> map = page.map(x -> TransferUtil.transferTo(x, ReimbursementSummaryVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
        return map;
    }
}

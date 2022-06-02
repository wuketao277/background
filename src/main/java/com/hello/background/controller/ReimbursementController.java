package com.hello.background.controller;

import com.hello.background.service.ReimbursementServise;
import com.hello.background.vo.ReimbursementItemVO;
import com.hello.background.vo.ReimbursementSummaryVO;
import com.hello.background.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author wuketao
 * @date 2021/11/28
 * @Description
 */
@RestController
@RequestMapping("reimbursement")
public class ReimbursementController {

    @Autowired
    private ReimbursementServise reimbursementServise;

    @PostMapping("save")
    public ReimbursementItemVO save(@RequestBody ReimbursementItemVO vo, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        vo.setUpdateTime(new Date());
        vo.setUpdateUserName(user.getUsername());
        return reimbursementServise.save(vo);
    }

    /**
     * 查询分页
     *
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryPage")
    public Page<ReimbursementItemVO> queryPage(Integer currentPage, Integer pageSize, String search, HttpSession session) {
        return reimbursementServise.queryPage(currentPage, pageSize, search, session);
    }

    /**
     * 查询分页
     *
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("querySummaryPage")
    public Page<ReimbursementSummaryVO> querySummaryPage(Integer currentPage, Integer pageSize, HttpSession session) {
        return reimbursementServise.querySummaryPage(currentPage, pageSize, session);
    }

    /**
     * 生成报销摘要
     */
    @PostMapping("generateReimbursementSummary")
    public void generateReimbursementSummary(HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        reimbursementServise.generateReimbursementSummary(user);
    }

    /**
     * 获取当前月总报销金额
     *
     * @return
     */
    @GetMapping("getCurrentMonthSumReimbursement")
    public Double getCurrentMonthSumReimbursement() {
        return reimbursementServise.getCurrentMonthSumReimbursement();
    }
}

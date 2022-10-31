package com.hello.background.controller;

import com.hello.background.service.ReimbursementServise;
import com.hello.background.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

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
     * 下载报销项详情
     *
     * @return
     */
    @GetMapping("downloadReimbursementItem")
    public void downloadReimbursementItem(@RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize, @RequestParam("search") String search, HttpSession session, HttpServletResponse response) {
        reimbursementServise.downloadReimbursementItem(currentPage, pageSize, search, session, response);
    }

    /**
     * 下载报销
     *
     * @return
     */
    @GetMapping("downloadReimbursementSummary")
    public void downloadReimbursementSummary(@RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize, @RequestParam("search") String search, HttpSession session, HttpServletResponse response) {
        reimbursementServise.downloadReimbursementSummary(currentPage, pageSize, search, session, response);
    }

    /**
     * 查询统计
     *
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryStatistics")
    public ReimbursementStatisticsResponse queryStatistics(Integer currentPage, Integer pageSize, String search, HttpSession session) {
        return reimbursementServise.queryStatistics(currentPage, pageSize, search, session);
    }

    /**
     * 查询分页
     *
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("querySummaryPage")
    public Page<ReimbursementSummaryVO> querySummaryPage(String search, Integer currentPage, Integer pageSize, HttpSession session) {
        return reimbursementServise.querySummaryPage(search, currentPage, pageSize, session);
    }

    /**
     * 生成报销摘要
     */
    @PostMapping("generateReimbursementSummary")
    public void generateReimbursementSummary(@RequestBody GenerateReimbursementRequest request, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        reimbursementServise.generateReimbursementSummary(request.getMonth(), user);
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

    /**
     * 通过主键删除报销
     *
     * @return
     */
    @DeleteMapping("deleteById")
    public void deleteById(@RequestParam Integer id) {
        reimbursementServise.deleteById(id);
    }

    /**
     * 审批通过选中项
     *
     * @param reimbursementItemVOList
     */
    @PostMapping("approveSelection")
    public void approveSelection(@RequestBody List<ReimbursementItemVO> reimbursementItemVOList) {
        reimbursementServise.approveSelection(reimbursementItemVOList);
    }
}

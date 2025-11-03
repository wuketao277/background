package com.hello.background.controller;

import com.hello.background.service.ReimbursementServise;
import com.hello.background.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
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
        return reimbursementServise.save(vo, user);
    }

    /**
     * 查询分页
     *
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryPage")
    public ReimbursementItemPageInfo queryPage(@RequestParam(value = "userName", required = false) String userName,
                                               @RequestParam(value = "approveStatus", required = false) String approveStatus,
                                               @RequestParam(value = "needPay", required = false) String needPay,
                                               @RequestParam(value = "date", required = false) String date,
                                               @RequestParam(value = "location", required = false) String location,
                                               @RequestParam(value = "company", required = false) String company,
                                               @RequestParam(value = "paymentMonth", required = false) String paymentMonth,
                                               @RequestParam(value = "type", required = false) String type,
                                               @RequestParam(value = "kind", required = false) String kind,
                                               @RequestParam(value = "invoiceNo", required = false) String invoiceNo,
                                               @RequestParam(value = "sum", required = false) String sum,
                                               @RequestParam(value = "description", required = false) String description,
                                               @RequestParam(value = "paymentMonthStart", required = false) String paymentMonthStart,
                                               @RequestParam(value = "paymentMonthEnd", required = false) String paymentMonthEnd,
                                               @RequestParam("currentPage") Integer currentPage,
                                               @RequestParam("pageSize") Integer pageSize, HttpSession session) {
        return reimbursementServise.queryPage(userName, approveStatus, needPay, date, location, company, paymentMonth, type, kind, invoiceNo, sum, description, paymentMonthStart, paymentMonthEnd, currentPage, pageSize, session);
    }

    /**
     * 下载报销项详情
     *
     * @return
     */
    @GetMapping("downloadReimbursementItem")
    public void downloadReimbursementItem(@RequestParam(value = "userName", required = false) String userName,
                                          @RequestParam(value = "approveStatus", required = false) String approveStatus,
                                          @RequestParam(value = "needPay", required = false) String needPay,
                                          @RequestParam(value = "date", required = false) String date,
                                          @RequestParam(value = "location", required = false) String location,
                                          @RequestParam(value = "company", required = false) String company,
                                          @RequestParam(value = "paymentMonth", required = false) String paymentMonth,
                                          @RequestParam(value = "type", required = false) String type,
                                          @RequestParam(value = "kind", required = false) String kind,
                                          @RequestParam(value = "invoiceNo", required = false) String invoiceNo,
                                          @RequestParam(value = "sum", required = false) String sum,
                                          @RequestParam(value = "description", required = false) String description,
                                          @RequestParam(value = "paymentMonth", required = false) String paymentMonthStart,
                                          @RequestParam(value = "paymentMonth", required = false) String paymentMonthEnd,
                                          @RequestParam("currentPage") Integer currentPage,
                                          @RequestParam("pageSize") Integer pageSize,
                                          HttpSession session, HttpServletResponse response) {
        reimbursementServise.downloadReimbursementItem(userName, approveStatus, needPay, date, location, company, paymentMonth, type, kind, invoiceNo, sum, description, paymentMonthStart, paymentMonthEnd, currentPage, pageSize, session, response);
    }

    /**
     * 查询分页
     *
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("querySummaryPage")
    public ReimbursementSummaryPageInfo querySummaryPage(@RequestParam(value = "company", required = false) String company,
                                                         @RequestParam(value = "userName", required = false) String userName,
                                                         @RequestParam(value = "paymentMonth", required = false) String paymentMonth,
                                                         @RequestParam(value = "sum", required = false) String sum,
                                                         @RequestParam("currentPage") Integer currentPage,
                                                         @RequestParam("pageSize") Integer pageSize, HttpSession session) {
        return reimbursementServise.querySummaryPage(company, userName, paymentMonth, sum, currentPage, pageSize, session);
    }

    /**
     * 下载报销
     *
     * @return
     */
    @GetMapping("downloadReimbursementSummary")
    public void downloadReimbursementSummary(@RequestParam(value = "company", required = false) String company,
                                             @RequestParam(value = "userName", required = false) String userName,
                                             @RequestParam(value = "paymentMonth", required = false) String paymentMonth,
                                             @RequestParam(value = "sum", required = false) String sum,
                                             @RequestParam("currentPage") Integer currentPage,
                                             @RequestParam("pageSize") Integer pageSize, HttpSession session, HttpServletResponse response) {
        reimbursementServise.downloadReimbursementSummary(company, userName, paymentMonth, sum, currentPage, pageSize, session, response);
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

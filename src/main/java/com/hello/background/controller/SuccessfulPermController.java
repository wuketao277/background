package com.hello.background.controller;

import com.hello.background.service.CandidateForCaseService;
import com.hello.background.service.ClientService;
import com.hello.background.service.SuccessfulPermService;
import com.hello.background.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author wuketao
 * @date 2020/2/3
 * @Description
 */
@RestController
@RequestMapping("successfulPerm")
public class SuccessfulPermController {
    @Autowired
    private SuccessfulPermService successfulPermService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CandidateForCaseService candidateForCaseService;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public SuccessfulPermVO save(@RequestBody SuccessfulPermVO vo, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        vo.setUpdateTime(new Date());
        vo.setUpdateUserName(user.getUsername());
        if (!StringUtils.isEmpty(vo.getClientId())) {
            vo.setClientName(Optional.ofNullable(clientService.queryById(vo.getClientId())).map(x -> x.getChineseName()).orElse(""));
        }
        SuccessfulPermVO successfulPermVO = successfulPermService.save(vo);
        // 更新候选人关联职位最新阶段
        if ("perm".equals(successfulPermVO.getType())
                && null != successfulPermVO.getClientId()
                && null != successfulPermVO.getCaseId()) {
            // 猎头业务，且候选人id和职位id不为空
            if (null != successfulPermVO.getGuaranteeDate() && new Date().compareTo(successfulPermVO.getGuaranteeDate()) > 0) {
                // 存在保证期且当前时间大于保证期，表示已成功
                candidateForCaseService.updateLastPhase(successfulPermVO.getCandidateId(), successfulPermVO.getCaseId(), "Successful");
                candidateForCaseService.updateFarthestPhase(successfulPermVO.getCandidateId(), successfulPermVO.getCaseId(), "Successful");
            } else if (null != successfulPermVO.getActualPaymentDate()) {
                // 实际付款日期不为空，更新最新阶段到Payment
                candidateForCaseService.updateLastPhase(successfulPermVO.getCandidateId(), successfulPermVO.getCaseId(), "Payment");
                candidateForCaseService.updateFarthestPhase(successfulPermVO.getCandidateId(), successfulPermVO.getCaseId(), "Payment");
            } else if (null != successfulPermVO.getInvoiceDate()) {
                // 实际付款日期不为空，更新最新阶段到Invoice
                candidateForCaseService.updateLastPhase(successfulPermVO.getCandidateId(), successfulPermVO.getCaseId(), "Invoice");
                candidateForCaseService.updateFarthestPhase(successfulPermVO.getCandidateId(), successfulPermVO.getCaseId(), "Invoice");
            }
        }
        return successfulPermVO;
    }

    /**
     * 查询分页
     *
     * @return
     */
    @PostMapping("queryPage")
    public Page<SuccessfulPermVO> queryPage(@RequestBody SuccessfulPermVOPageRequest request, HttpSession session) {
        return successfulPermService.queryPage(request, session);
    }

    /**
     * 下载成功case
     *
     * @return
     */
    @GetMapping("downloadSuccessfulCase")
    public void downloadSuccessfulCase(HttpSession session, HttpServletResponse response) {
        successfulPermService.downloadSuccessfulCase(session, response);
    }

    /**
     * 查询统计
     *
     * @return
     */
    @PostMapping("queryStatistics")
    public SuccessfulCaseStatisticsResponse queryStatistics(@RequestBody SuccessfulPermVOPageRequest request, HttpSession session) {
        return successfulPermService.queryStatistics(request, session);
    }

    /**
     * 通过主键删除
     *
     * @param request
     * @return
     */
    @PostMapping("deleteById")
    public String deleteById(@RequestBody DeleteSuccessfulPermRequest request) {
        return successfulPermService.deleteById(request.getId());
    }

    /**
     * 当日入职列表
     *
     * @return
     */
    @GetMapping("todayOnboardList")
    public List<TodayOnboardInfoVO> todayOnboardList() {
        return successfulPermService.todayOnboardList();
    }
}

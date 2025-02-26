package com.hello.background.controller;

import com.hello.background.service.CandidateForCaseService;
import com.hello.background.service.CommentService;
import com.hello.background.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author wuketao
 * @date 2019/12/22
 * @Description
 */
@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CandidateForCaseService candidateForCaseService;

    @PostMapping("save")
    public CommentVO save(@RequestBody CommentVO vo, HttpSession session) {
        if (null != vo.getInterviewTime()) {
            vo.setInterviewTime(vo.getInterviewTime().plusHours(8));
        }
        UserVO user = (UserVO) session.getAttribute("user");
        vo.setInputTime(LocalDateTime.now());
        vo.setRealname(user.getRealname());
        vo.setUsername(user.getUsername());
        CommentVO commentVO = commentService.save(vo);
        // 更新候选人关联职位最新阶段和最远阶段
        candidateForCaseService.updateLastPhase(commentVO.getCandidateId(), commentVO.getCaseId(), commentVO.getPhase());
        candidateForCaseService.updateFarthestPhase(commentVO.getCandidateId(), commentVO.getCaseId(), commentVO.getPhase());
        return commentVO;
    }

    /**
     * 通过主键删除
     *
     * @param id 主键
     */
    @GetMapping("deleteById")
    public void deleteById(Integer id) {
        commentService.deleteById(id);
    }

    /**
     * 查询候选人评论
     *
     * @param candidateId
     * @return
     */
    @GetMapping("findAllByCandidateId")
    public List<CommentVO> findAllByCandidateId(Integer candidateId) {
        return commentService.findAllByCandidateId(candidateId);
    }

    /**
     * 查询候选人评论，倒序排序
     *
     * @param candidateId
     * @return
     */
    @GetMapping("findAllByCandidateIdOrderByDesc")
    public List<CommentVO> findAllByCandidateIdOrderByDesc(Integer candidateId) {
        return commentService.findAllByCandidateIdOrderByDesc(candidateId);
    }

    /**
     * 计算KPI
     *
     * @param request
     * @return
     */
    @PostMapping("calcKPI")
    public List<KPIPerson> calcKPI(@RequestBody CalcKPIRequest request, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        List<KPIPerson> kpiPersonList = commentService.calcKPI(request.getStartDate(), request.getEndDate(), request.getScope(), user, request.isKpiOnlyShowCheck());
        return kpiPersonList;
    }

    /**
     * 保存KPI
     *
     * @return
     */
    @PostMapping("saveKPI")
    public void saveKPI(@RequestBody SaveKPIRequest request) {
        commentService.saveKPI(request.getMonth());
    }

    /**
     * 下载KPI Excel文件
     *
     * @param response
     */
    @GetMapping("downloadKPI")
    public void downloadKPI(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String scope, @RequestParam boolean kpiOnlyShowCheck, HttpSession session, HttpServletResponse response) {
        UserVO user = (UserVO) session.getAttribute("user");
        commentService.downloadKPI(Arrays.asList(startDate, endDate), scope, user, response, kpiOnlyShowCheck);
    }

    /**
     * 通过开始时间、结束时间、录入人 查找评论
     *
     * @param beginDate
     * @param endDate
     * @param userName
     * @return
     */
    @GetMapping("findCommentsByTimeAndUsername")
    public List<CommentVO> findCommentsByTimeAndUsername(@RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate, @RequestParam("userName") String userName) {
        return commentService.findCommentsByTimeAndUsername(beginDate, endDate, userName);
    }

    /**
     * 通过评论查询候选人 最多返回100条
     *
     * @param search 搜索关键字
     * @return
     */
    @GetMapping("queryCandidateByCommentLimit100")
    public List<CandidateVO> queryCandidateByCommentLimit100(@RequestParam("search") String search) {
        search = "%" + search + "%";
        return commentService.queryCandidateByCommentLimit100(search);
    }

    /**
     * 查询interviewPlan数据
     *
     * @param session
     * @return
     */
    @GetMapping("queryInterviewPlan")
    public List<InterviewPlanVO> queryInterviewPlan(@RequestParam("range") String range, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        return commentService.queryInterviewPlan(range, userVO);
    }

    /**
     * 面试分页查询
     *
     * @returnost
     */
    @PostMapping("queryInterviewPage")
    public Page<InterviewVO> queryInterviewPage(@RequestBody QueryInterviewRequest request) {
        return commentService.queryInterviewPage(request.getSearch(), request.getCurrentPage(), request.getPageSize());
    }

    /**
     * 下载面试
     *
     * @return
     */
    @GetMapping("downloadInterviews")
    public void downloadInterviews(@RequestParam(value = "loginName", required = false) String loginName, @RequestParam(value = "clientName", required = false) String clientName, @RequestParam(value = "title", required = false) String title, @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate, @RequestParam(value = "phase", required = false) String phase, @RequestParam(value = "currentPage", required = false) Integer currentPage, @RequestParam(value = "pageSize", required = false) Integer pageSize, HttpSession session, HttpServletResponse response) {
        commentService.downloadInterviews(loginName, clientName, title, startDate, endDate, phase, currentPage, pageSize, session, response);
    }
}

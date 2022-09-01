package com.hello.background.controller;

import com.hello.background.service.CommentService;
import com.hello.background.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
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

    @RequestMapping("save")
    public CommentVO save(@RequestBody CommentVO vo, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        vo.setInputTime(LocalDateTime.now());
        vo.setRealname(user.getRealname());
        vo.setUsername(user.getUsername());
        return commentService.save(vo);
    }

    @RequestMapping("findAllByCandidateId")
    public List<CommentVO> findAllByCandidateId(Integer candidateId) {
        return commentService.findAllByCandidateId(candidateId);
    }

    @PostMapping("calcKPI")
    public List<KPIPerson> calcKPI(@RequestBody CalcKPIRequest request) {
        // 拿到前台传入的日期要进行+1操作。因为前端给的日期是差1天。
        LocalDate start = LocalDate.parse(request.getDates().get(0).substring(0, 10)).plusDays(1);
        LocalDate end = LocalDate.parse(request.getDates().get(1).substring(0, 10)).plusDays(1);
        return commentService.calcKPI(start, end);
    }

    /**
     * 下载KPI Excel文件
     *
     * @param response
     */
    @GetMapping("downloadKPI")
    public void downloadKPI(@RequestParam String startDate, @RequestParam String endDate, HttpServletResponse response) {
        commentService.downloadKPI(Arrays.asList(startDate, endDate), response);
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
}

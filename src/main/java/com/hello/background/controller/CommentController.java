package com.hello.background.controller;

import com.hello.background.service.CommentService;
import com.hello.background.vo.CalcKPIRequest;
import com.hello.background.vo.CommentVO;
import com.hello.background.vo.KPIPerson;
import com.hello.background.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
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
        return commentService.calcKPI(request);
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
}

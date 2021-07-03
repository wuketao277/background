package com.hello.background.controller;

import com.hello.background.service.CommentService;
import com.hello.background.vo.CalcKPIRequest;
import com.hello.background.vo.CommentVO;
import com.hello.background.vo.KPIPerson;
import com.hello.background.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

package com.hello.background.controller;

import com.hello.background.service.PRCCommentService;
import com.hello.background.vo.PRCCommentVO;
import com.hello.background.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wuketao
 * @date 2025/12/23
 * @Description
 */
@RestController
@RequestMapping("prcComment")
public class PRCCommentController {
    @Autowired
    private PRCCommentService prcCommentService;

    @PostMapping("save")
    public PRCCommentVO save(@RequestBody PRCCommentVO vo, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        vo.setInputTime(LocalDateTime.now());
        vo.setUsername(user.getUsername());
        return prcCommentService.save(vo);
    }

    /**
     * 通过主键删除
     *
     * @param id 主键
     */
    @GetMapping("deleteById")
    public void deleteById(@RequestParam("id") Integer id) {
        prcCommentService.deleteById(id);
    }

    /**
     * 查询PRC评论
     *
     * @param prcId
     * @return
     */
    @GetMapping("findAllByPRCIdOrderByDesc")
    public List<PRCCommentVO> findAllByPRCIdOrderByDesc(@RequestParam("prcId") Integer prcId) {
        return prcCommentService.findAllByPRCIdOrderByDesc(prcId);
    }
}

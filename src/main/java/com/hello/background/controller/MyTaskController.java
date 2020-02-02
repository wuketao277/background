package com.hello.background.controller;

import com.hello.background.service.MyTaskService;
import com.hello.background.vo.MyTaskVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wuketao
 * @date 2020/2/2
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("myTask")
public class MyTaskController {

    @Autowired
    private MyTaskService myTaskService;

    /**
     * 保存任务
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public MyTaskVO save(@RequestBody MyTaskVO vo, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        vo.setCreateDateTime(LocalDateTime.now());
        vo.setCreateUserId(userVO.getUsername());
        return myTaskService.save(vo);
    }

    /**
     * 保存任务给创建人自己
     *
     * @param vo
     * @return
     */
    @PostMapping("saveTaskToSelf")
    public MyTaskVO saveTaskToSelf(@RequestBody MyTaskVO vo, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        vo.setCreateDateTime(LocalDateTime.now());
        vo.setCreateUserId(userVO.getUsername());
        // 任务的执行人，就是创建人
        vo.setExecuteUserId(userVO.getUsername());
        return myTaskService.save(vo);
    }

    /**
     * 通过后续人id查询
     *
     * @param relativeCandidateId
     * @return
     */
    @GetMapping("findByRelativeCandidateId")
    public List<MyTaskVO> findByRelativeCandidateId(@RequestParam Integer relativeCandidateId) {
        return myTaskService.findByRelativeCandidateId(relativeCandidateId);
    }
}

package com.hello.background.controller;

import com.hello.background.service.MyTaskService;
import com.hello.background.vo.MyTaskVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
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
        // 由于后台接收前台数据时，日期会少一天。这里要单独做一个补偿
        vo.setExecuteDate(vo.getExecuteDate().plusDays(1));
        // 获取当前用户
        UserVO userVO = (UserVO) session.getAttribute("user");
        vo.setCreateDateTime(LocalDateTime.now());
        vo.setCreateUserName(userVO.getUsername());
        vo.setCreateRealName(userVO.getRealname());
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
        // 由于后台接收前台数据时，日期会少一天。这里要单独做一个补偿
        vo.setExecuteDate(vo.getExecuteDate().plusDays(1));
        // 获取当前用户
        UserVO userVO = (UserVO) session.getAttribute("user");
        vo.setCreateDateTime(LocalDateTime.now());
        vo.setCreateUserName(userVO.getUsername());
        vo.setCreateRealName(userVO.getRealname());
        // 任务的执行人，就是创建人
        vo.setExecuteUserName(userVO.getUsername());
        vo.setExecuteRealName(userVO.getRealname());
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

    /**
     * 查询本人今日任务
     *
     * @return 任务集合
     */
    @GetMapping("queryTodayTaskForMe")
    public List<MyTaskVO> queryTodayTaskForMe(HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        if (null == userVO) {
            return Collections.emptyList();
        }
        return myTaskService.findByExecuteUserNameAndExecuteDate(userVO.getUsername(), LocalDate.now());
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryMyTaskPage")
    public Page<MyTaskVO> queryMyTaskPage(String search, Integer currentPage, Integer pageSize) {
        search = "%" + search + "%";
        return myTaskService.queryMyTaskPage(search, currentPage, pageSize);
    }
}

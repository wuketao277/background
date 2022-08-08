package com.hello.background.controller;

import com.hello.background.service.LessonQuestionService;
import com.hello.background.service.LessonService;
import com.hello.background.service.LessonStudyRecordService;
import com.hello.background.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

/**
 * @author wuketao
 * @date 2020/8/3
 * @Description
 */
@RestController
@RequestMapping("training")
public class TrainingController {

    @Autowired
    private LessonService lessonService;
    @Autowired
    private LessonQuestionService lessonQuestionService;
    @Autowired
    private LessonStudyRecordService lessonStudyRecordService;

    /**
     * 保存课程
     *
     * @param vo
     * @return
     */
    @PostMapping("saveLesson")
    public LessonVO saveLesson(@RequestBody LessonVO vo, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        vo.setTeacherId(user.getId());
        vo.setTeacherUserName(user.getUsername());
        vo.setTeacherRealName(user.getRealname());
        return lessonService.save(vo);
    }

    /**
     * 课程查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryLessonByPage")
    public Page<LessonVO> queryLessonByPage(String search, Integer currentPage, Integer pageSize) {
        search = "%" + search + "%";
        return lessonService.queryPage(search, currentPage, pageSize);
    }

    /**
     * 通过id查询课程
     *
     * @param id 课程id
     * @return 课程
     */
    @GetMapping("queryLessonById")
    public LessonVO queryLessonById(Integer id) {
        return lessonService.queryById(id);
    }

    /**
     * 保存课程问题
     *
     * @param vo
     * @return
     */
    @PostMapping("saveLessonQuestion")
    public LessonQuestionVO saveLessonQuestion(@RequestBody LessonQuestionVO vo) {
        return lessonQuestionService.save(vo);
    }

    /**
     * 通过课程id查询课程的所有问题
     *
     * @param lessonId 课程id
     * @return 课程问题
     */
    @GetMapping("queryLessonQuestionByLessonId")
    public List<LessonQuestionVO> queryLessonQuestionByLessonId(Integer lessonId) {
        return lessonQuestionService.findByLessonId(lessonId);
    }

    /**
     * 通过课程问题id删除课程问题
     *
     * @param id 课程问题id
     */
    @DeleteMapping("deleteLessonQuestionById")
    public void deleteLessonQuestionById(Integer id) {
        lessonQuestionService.deleteById(id);
    }

    /**
     * 课程问题查询分页
     *
     * @param lessonId    课程id
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryLessonQuestionByPage")
    public Page<LessonQuestionVO> queryLessonQuestionByPage(@RequestParam("lessonId") Integer lessonId, @RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize) {
        return lessonQuestionService.queryPage(lessonId, currentPage, pageSize);
    }

    /**
     * 当前用户是否在课程上签到
     *
     * @param lessonId
     * @param session
     * @return
     */
    @GetMapping("isSignInForLessonStudyRecord")
    public boolean isSignInForLessonStudyRecord(Integer lessonId, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        LessonStudyRecordVO lessonStudyRecordVO = lessonStudyRecordService.findByLessonIdAndUsername(lessonId, user.getUsername());
        return Optional.ofNullable(lessonStudyRecordVO).map(x -> x.getSignInTime()).isPresent();
    }

    /**
     * 当前用户在课程上签到
     *
     * @param lessonId
     * @param session
     * @return
     */
    @GetMapping("signInForLessonStudyRecord")
    public void signInForLessonStudyRecord(Integer lessonId, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        LessonStudyRecordVO lessonStudyRecordVO = lessonStudyRecordService.findByLessonIdAndUsername(lessonId, user.getUsername());
        if (null == lessonStudyRecordVO) {
            lessonStudyRecordVO = new LessonStudyRecordVO();
        }
        LessonVO lessonVO = lessonService.queryById(lessonId);
        lessonStudyRecordVO.setLessonId(lessonId);
        lessonStudyRecordVO.setLessonName(lessonVO.getLessonName());
        lessonStudyRecordVO.setUsername(user.getUsername());
        lessonStudyRecordVO.setRealname(user.getRealname());
        lessonStudyRecordVO.setSignInTime(LocalDateTime.now());
        lessonStudyRecordService.save(lessonStudyRecordVO);
    }

    /**
     * 当前用户是否完成课程
     *
     * @param lessonId
     * @param session
     * @return
     */
    @GetMapping("isFinishForLessonStudyRecord")
    public boolean isFinishForLessonStudyRecord(Integer lessonId, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        LessonStudyRecordVO lessonStudyRecordVO = lessonStudyRecordService.findByLessonIdAndUsername(lessonId, user.getUsername());
        return Optional.ofNullable(lessonStudyRecordVO).map(x -> x.getFinishTime()).isPresent();
    }

    /**
     * 完成当前用户课程
     *
     * @param lessonId
     * @param session
     * @return
     */
    @GetMapping("finishForLessonStudyRecord")
    public void finishForLessonStudyRecord(Integer lessonId, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        LessonStudyRecordVO lessonStudyRecordVO = lessonStudyRecordService.findByLessonIdAndUsername(lessonId, user.getUsername());
        if (null == lessonStudyRecordVO) {
            lessonStudyRecordVO = new LessonStudyRecordVO();
        }
        LessonVO lessonVO = lessonService.queryById(lessonId);
        lessonStudyRecordVO.setLessonId(lessonId);
        lessonStudyRecordVO.setLessonName(lessonVO.getLessonName());
        lessonStudyRecordVO.setUsername(user.getUsername());
        lessonStudyRecordVO.setRealname(user.getRealname());
        lessonStudyRecordVO.setFinishTime(LocalDateTime.now());
        // 计算学习时长
        if (null != lessonStudyRecordVO.getSignInTime()) {
            long secondSum = lessonStudyRecordVO.getFinishTime().toEpochSecond(ZoneOffset.ofHours(8)) - lessonStudyRecordVO.getSignInTime().toEpochSecond(ZoneOffset.ofHours(8));
            BigDecimal d = BigDecimal.valueOf(secondSum).divide(BigDecimal.valueOf(3600), 1, RoundingMode.HALF_DOWN);
            lessonStudyRecordVO.setHours(d);
        }
        lessonStudyRecordService.save(lessonStudyRecordVO);
    }

    /**
     * 课程学习记录查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryLessonStudyRecordByPage")
    public Page<LessonStudyRecordVO> queryLessonStudyRecordByPage(String search, Integer currentPage, Integer pageSize) {
        return lessonStudyRecordService.queryPage(search, currentPage, pageSize);
    }

    /**
     * 通过id查询课程学习记录
     *
     * @param id
     * @return
     */
    @GetMapping("queryLessonStudyRecordById")
    public LessonStudyRecordVO queryLessonStudyRecordById(Integer id) {
        return lessonStudyRecordService.queryById(id);
    }

    /**
     * 保存学习记录得分
     *
     * @param requestVO
     */
    @PostMapping("saveStudyRecordScore")
    public void saveStudyRecordScore(@RequestBody SaveStudyRecordScoreRequestVO requestVO) {
        lessonStudyRecordService.saveStudyRecordScore(requestVO.getId(), requestVO.getScore());
    }
}

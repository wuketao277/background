package com.hello.background.controller;

import com.hello.background.constant.CandidateForCaseStatusEnum;
import com.hello.background.constant.JobTypeEnum;
import com.hello.background.constant.RoleEnum;
import com.hello.background.domain.CandidateForCase;
import com.hello.background.service.CandidateForCaseService;
import com.hello.background.service.CandidateService;
import com.hello.background.service.CaseService;
import com.hello.background.service.CommentService;
import com.hello.background.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 候选人与职位关联表 控制器
 *
 * @author wuketao
 * @date 2019/12/14
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("candidateForCase")
public class CandidateForCaseController {

    @Autowired
    private CandidateForCaseService candidateForCaseService;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private CaseService caseService;
    @Autowired
    private CommentService commentService;

    // 创建线程池
    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(100, 100, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));

    /**
     * 保存 候选人与职位关联 信息
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public CandidateForCaseVO save(@RequestBody CandidateForCaseVO vo, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        // 保存当前时间
        vo.setCreateTime(LocalDateTime.now());
        // 保存记录创建人
        vo.setCreateUserName(userVO.getUsername());
        // 保存候选人在职位上的初始状态
        vo.setStatus(CandidateForCaseStatusEnum.PREPARE.getCode());
        // 通过候选人id获取候选人信息
        Integer candidateId = vo.getCandidateId();
        CandidateVO candidateVO = candidateService.findById(candidateId, userVO);
        if (null != candidateVO) {
            vo.setChineseName(candidateVO.getChineseName());
            vo.setEnglishName(candidateVO.getEnglishName());
            return candidateForCaseService.save(vo);
        }
        return null;
    }

    /**
     * 职位下，从旧case拷贝候选人到新case
     *
     * @param vo
     * @return
     */
    @PostMapping("copyFromOldCase")
    public void copyFromOldCase(@RequestBody CopyFromOldCaseVO vo) {
        candidateForCaseService.copyFromOldCase(vo);
    }

    /**
     * 保存 候选人与职位关联 信息
     *
     * @param vo
     * @return
     */
    @PostMapping("saveSimple")
    public boolean saveSimple(@RequestBody CandidateForCaseSimpleVO vo, HttpSession session) {
        List<CandidateForCase> oldRecordList = candidateForCaseService.findByCandidateIdAndCaseId(vo.getCandidateId(), vo.getCaseId());
        if (CollectionUtils.isEmpty(oldRecordList)) {
            UserVO userVO = (UserVO) session.getAttribute("user");
            CandidateForCaseVO candidateForCaseVO = new CandidateForCaseVO();
            // 获取候选人信息
            CandidateVO candidateVO = candidateService.findById(vo.getCandidateId(), userVO);
            candidateForCaseVO.setCandidateId(candidateVO.getId());
            candidateForCaseVO.setChineseName(candidateVO.getChineseName());
            candidateForCaseVO.setEnglishName(candidateVO.getEnglishName());
            // 获取职位信息
            CaseVO caseVO = caseService.findById(vo.getCaseId(), userVO);
            candidateForCaseVO.setCaseId(caseVO.getId());
            candidateForCaseVO.setClientId(caseVO.getClientId());
            candidateForCaseVO.setTitle(caseVO.getTitle());
            // 保存当前时间
            candidateForCaseVO.setCreateTime(LocalDateTime.now());
            // 保存记录创建人
            candidateForCaseVO.setCreateUserName(userVO.getUsername());
            // 保存候选人在职位上的初始状态
            candidateForCaseVO.setStatus(CandidateForCaseStatusEnum.PREPARE.getCode());
            candidateForCaseService.save(candidateForCaseVO);
        }
        return true;
    }

    /**
     * 更新状态
     *
     * @param id        候选人与职位关联id
     * @param newStatus 新状态
     * @return
     */
    @GetMapping("updateStatus")
    public boolean updateStatus(@RequestParam Integer id, @RequestParam String newStatus) {
        return candidateForCaseService.updateStatus(id, newStatus);
    }

    /**
     * 通过职位id下载所有推荐的候选人信息
     *
     * @param caseId 职位id
     * @return 职位推荐候选人信息
     */
    @GetMapping("downloadCandidates")
    public void downloadCandidates(@RequestParam Integer caseId, HttpServletResponse response, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        candidateForCaseService.downloadCandidates(caseId, response, userVO.getRoles().contains(RoleEnum.ADMIN));
    }

    /**
     * 通过职位id获取所有推荐的候选人信息
     *
     * @param caseId 职位id
     * @return 职位推荐候选人信息
     */
    @GetMapping("findByCaseId")
    public List<CandidateForCaseVO> findByCaseId(@RequestParam Integer caseId, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        List<CandidateForCaseVO> voList = candidateForCaseService.findByCaseId(caseId);
        voList.sort(Comparator.comparing(CandidateForCaseVO::getCandidateId).reversed());
        voList.sort(Comparator.comparing(CandidateForCaseVO::getAttention).reversed());
        // 兼职只能看见自己的数据
        if (JobTypeEnum.PARTTIME.compareTo(userVO.getJobType()) == 0) {
            voList = voList.stream().filter(vo -> userVO.getUsername().equals(vo.getCreateUserName())).collect(Collectors.toList());
        }
        // 异步处理查询候选人客户重复标签
        voList.parallelStream().forEach(vo -> {
            CandidateVO candidateVO = candidateService.findById(vo.getCandidateId(), userVO);
            if (null != candidateVO) {
                vo.setCandidateClientRepeatedLabels(candidateVO.getCandidateClientRepeatedLabels());
            }
        });
        return voList;
    }

    /**
     * 通过职位id获取关联推荐候选人信息
     *
     * @param caseId 职位id
     * @return 职位推荐候选人信息
     */
    @GetMapping("findAttentionByCaseId")
    public List<CandidateForCaseVO> findAttentionByCaseId(@RequestParam Integer caseId, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        List<CandidateForCaseVO> voList = candidateForCaseService.findAttentionByCaseId(caseId);
        voList.sort(Comparator.comparing(CandidateForCaseVO::getCandidateId).reversed());
        // 兼职只能看见自己的数据
        if (JobTypeEnum.PARTTIME.compareTo(userVO.getJobType()) == 0) {
            voList = voList.stream().filter(vo -> userVO.getUsername().equals(vo.getCreateUserName())).collect(Collectors.toList());
        }
        // 异步处理查询候选人客户重复标签
        voList.parallelStream().forEach(vo -> {
            CandidateVO candidateVO = candidateService.findById(vo.getCandidateId(), userVO);
            if (null != candidateVO) {
                vo.setCandidateClientRepeatedLabels(candidateVO.getCandidateClientRepeatedLabels());
            }
        });
        return voList;
    }

    /**
     * 通过候选人id获取所有职位推荐候选人信息
     *
     * @param candidateId 候选人id
     * @return 职位推荐候选人信息
     */
    @GetMapping("findByCandidateId")
    public List<CandidateForCaseVO> findByCandidateId(@RequestParam Integer candidateId, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        List<CandidateForCaseVO> voList = candidateForCaseService.findByCandidateId(candidateId);
        // 兼职只能看见自己的数据
        if (JobTypeEnum.PARTTIME.compareTo(userVO.getJobType()) == 0) {
            voList = voList.stream().filter(vo -> userVO.getUsername().equals(vo.getCreateUserName())).collect(Collectors.toList());
        }
        return voList;
    }

    /**
     * 通过id删除
     *
     * @param id 候选人与职位关联id
     * @return
     */
    @GetMapping("deleteById")
    public boolean deleteById(@RequestParam Integer id) {
        candidateForCaseService.deleteById(id);
        return true;
    }

    /**
     * 更新关注字段
     *
     * @return
     */
    @PostMapping("updateAttention")
    public boolean updateAttention(@RequestBody UpdateCandidateForCaseAttentionVO request) {
        candidateForCaseService.updateAttention(request.getId(), request.getAttention());
        return true;
    }
}

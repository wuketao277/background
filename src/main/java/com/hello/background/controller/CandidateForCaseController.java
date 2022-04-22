package com.hello.background.controller;

import com.hello.background.constant.CandidateForCaseStatusEnum;
import com.hello.background.domain.CandidateForCase;
import com.hello.background.service.CandidateForCaseService;
import com.hello.background.service.CandidateService;
import com.hello.background.service.CaseService;
import com.hello.background.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

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
        CandidateVO candidateVO = candidateService.findById(candidateId);
        if (null != candidateVO) {
            vo.setChineseName(candidateVO.getChineseName());
            vo.setEnglishName(candidateVO.getEnglishName());
            return candidateForCaseService.save(vo);
        }
        return null;
    }

    /**
     * 保存 候选人与职位关联 信息
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
            CandidateVO candidateVO = candidateService.findById(vo.getCandidateId());
            candidateForCaseVO.setCandidateId(candidateVO.getId());
            candidateForCaseVO.setChineseName(candidateVO.getChineseName());
            candidateForCaseVO.setEnglishName(candidateVO.getEnglishName());
            // 获取职位信息
            CaseVO caseVO = caseService.findById(vo.getCaseId());
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
     * 通过职位id获取所有职位推荐候选人信息
     *
     * @param caseId 职位id
     * @return 职位推荐候选人信息
     */
    @GetMapping("findByCaseId")
    public List<CandidateForCaseVO> findByCaseId(@RequestParam Integer caseId) {
        List<CandidateForCaseVO> voList = candidateForCaseService.findByCaseId(caseId);
        voList.sort(Comparator.comparing(CandidateForCaseVO::getCandidateId).reversed());
        return voList;
    }

    /**
     * 通过候选人id获取所有职位推荐候选人信息
     *
     * @param candidateId 候选人id
     * @return 职位推荐候选人信息
     */
    @GetMapping("findByCandidateId")
    public List<CandidateForCaseVO> findByCandidateId(@RequestParam Integer candidateId) {
        List<CandidateForCaseVO> voList = candidateForCaseService.findByCandidateId(candidateId);
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

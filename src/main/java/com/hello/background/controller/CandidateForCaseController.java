package com.hello.background.controller;

import com.hello.background.constant.CandidateForCaseStatusEnum;
import com.hello.background.service.CandidateForCaseService;
import com.hello.background.service.CandidateService;
import com.hello.background.vo.CandidateForCaseVO;
import com.hello.background.vo.CandidateVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
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
        return candidateForCaseService.findByCaseId(caseId);
    }
}

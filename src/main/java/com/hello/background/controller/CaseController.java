package com.hello.background.controller;

import com.hello.background.constant.CaseStatusEnum;
import com.hello.background.service.CaseAttentionService;
import com.hello.background.service.CaseService;
import com.hello.background.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

/**
 * @author wuketao
 * @date 2019/12/29
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("case")
public class CaseController {
    @Autowired
    private CaseService caseService;
    @Autowired
    private CaseAttentionService caseAttentionService;

    @PostMapping("save")
    public CaseVO save(@RequestBody CaseVO vo, HttpSession session) {
        if (null == vo.getId()) {
            UserVO userVO = (UserVO) session.getAttribute("user");
            vo.setCreateUserId(userVO.getUsername());
            vo.setCreateTime(LocalDateTime.now());
        }
        return caseService.save(vo);
    }

    /**
     * 查询分页
     *
     * @param request
     * @return
     */
    @PostMapping("queryPage")
    public Page<CaseVO> queryPage(@RequestBody CaseQueryPageRequest request, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        return caseService.queryPage(request, userVO);
    }

    /**
     * 查询
     *
     * @param search 搜索关键字
     * @return
     */
    @GetMapping("query")
    public List<CaseVO> query(@RequestParam("search") @NotEmpty String search, @RequestParam("status") String status) {
        search = "%" + search + "%";
        if (Strings.isBlank(status) || "ALL".equals(status)) {
            return caseService.queryByTitle(search);
        } else {
            return caseService.queryByTitleAndStatus(search, CaseStatusEnum.valueOf(status));
        }
    }

    /**
     * 通过职位id查找
     *
     * @param id
     * @return
     */
    @GetMapping("queryById")
    public CaseVO queryById(Integer id) {
        return caseService.findById(id);
    }

    /**
     * 查询职位关注
     *
     * @param caseId
     * @param session
     * @return
     */
    @GetMapping("queryCaseAttentionByCaseId")
    public boolean queryCaseAttentionByCaseId(Integer caseId, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        return caseAttentionService.queryAttentionByCaseIdAndUserName(caseId, userVO.getUsername());
    }

    /**
     * 查询当前用户所有职位关注
     *
     * @param session
     * @return
     */
    @GetMapping("queryAllCaseAttention")
    public List<CaseAttention4ClientVO> queryAllCaseAttention(@RequestParam boolean onlyShowMyselfCandidate, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        List<CaseAttention4ClientVO> caseAttention4ClientVOS = caseAttentionService.queryAllCaseAttention(userVO);
        if (onlyShowMyselfCandidate) {
            // 如果勾选了只关注自己的候选人复选框，就要把其他人的候选人排除
            Iterator<CaseAttention4ClientVO> caseAttention4ClientVOIterator = caseAttention4ClientVOS.iterator();
            while (caseAttention4ClientVOIterator.hasNext()) {
                CaseAttention4ClientVO caseAttention4ClientVO = caseAttention4ClientVOIterator.next();
                Iterator<CaseAttention4CaseVO> caseAttention4CaseVOIterator = caseAttention4ClientVO.getCaseList().iterator();
                while (caseAttention4CaseVOIterator.hasNext()) {
                    CaseAttention4CaseVO caseAttention4CaseVO = caseAttention4CaseVOIterator.next();
                    Iterator<CaseAttention4CandidateVO> caseAttention4CandidateVOIterator = caseAttention4CaseVO.getCandidateList().iterator();
                    while (caseAttention4CandidateVOIterator.hasNext()) {
                        CaseAttention4CandidateVO caseAttention4CandidateVO = caseAttention4CandidateVOIterator.next();
                        if (!caseAttention4CandidateVO.getLatestCommentUsername().equals(userVO.getUsername())) {
                            caseAttention4CandidateVOIterator.remove();
                        }
                    }
                }
            }
        }
        return caseAttention4ClientVOS;
    }

    /**
     * 查询当前用户所有职位关注
     *
     * @param session
     * @return
     */
    @GetMapping("queryAllCaseAttention2")
    public List<CaseAttentionVO> queryAllCaseAttention2(HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        return caseAttentionService.queryAllCaseAttention2(userVO);
    }

    /**
     * 查询所有用户所有职位关注
     *
     * @return
     */
    @GetMapping("queryAllUserCaseAttention")
    public List<CaseAttention4ClientVO> queryAllUserCaseAttention() {
        return caseAttentionService.queryAllUserCaseAttention();
    }

    /**
     * 查询当前用户所有对接的职位
     *
     * @param session
     * @return
     */
    @GetMapping("queryAllCWCase")
    public List<CaseAttention4ClientVO> queryAllCWCase(HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        return caseAttentionService.queryAllCWCase(userVO);
    }

    /**
     * 更新关注职位
     *
     * @param vo
     * @param session
     */
    @PostMapping("updateCaseAttention")
    public void updateCaseAttention(@RequestBody UpdateCaseAttentionVO vo, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        if (vo.isAttention()) {
            caseAttentionService.addAttention(vo.getCaseId(), userVO);
        } else {
            caseAttentionService.removeAttention(vo.getCaseId(), userVO.getUsername());
        }
    }

    /**
     * 通过主键删除
     *
     * @param request
     * @return
     */
    @PostMapping("deleteById")
    public String deleteById(@RequestBody DeleteCaseRequest request) {
        return caseService.deleteById(request.getId());
    }


    /**
     * 清空体验岗位
     *
     * @return
     */
    @GetMapping("clearExperience")
    public void clearExperience() {
        caseService.clearExperience();
    }
}

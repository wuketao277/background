package com.hello.background.controller;

import com.hello.background.service.CaseAttentionService;
import com.hello.background.service.CaseService;
import com.hello.background.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
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
        UserVO userVO = (UserVO) session.getAttribute("user");
        vo.setCreateUserId(userVO.getUsername());
        vo.setCreateTime(LocalDateTime.now());
        return caseService.save(vo);
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryPage")
    public Page<CaseVO> queryPage(@RequestParam("search") String search, @RequestParam("searchStatus") String searchStatus, @RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize) {
        return caseService.queryPage(search, searchStatus, currentPage, pageSize);
    }

    /**
     * 查询
     *
     * @param search 搜索关键字
     * @return
     */
    @GetMapping("query")
    public List<CaseVO> query(@NotEmpty String search) {
        search = "%" + search + "%";
        return caseService.query(search);
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
    public List<CaseAttention4ClientVO> queryAllCaseAttention(HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        return caseAttentionService.queryAllCaseAttention(userVO);
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
}

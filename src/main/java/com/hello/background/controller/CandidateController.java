package com.hello.background.controller;

import com.alibaba.fastjson.JSONObject;
import com.hello.background.service.CandidateService;
import com.hello.background.service.ResumeService;
import com.hello.background.vo.CandidateVO;
import com.hello.background.vo.ResumeVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author wuketao
 * @date 2019/12/14
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;
    @Autowired
    private ResumeService resumeService;

    /**
     * 上传文件
     *
     * @param request
     * @return
     */
    @PostMapping("uploadFile")
    public JSONObject uploadFile(HttpServletRequest request) {
        return candidateService.analysisUploadFile(request);
    }

    /**
     * 保存候选人信息
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public CandidateVO save(@RequestBody CandidateVO vo, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        return candidateService.save(vo, userVO);
    }

    /**
     * 保存候选人简历信息
     *
     * @param vo
     * @return
     */
    @PostMapping("saveResume")
    public void saveResume(@RequestBody @Valid ResumeVO vo) {
        resumeService.save(vo);
    }

    /**
     * 通过候选人Id查找候选人简历信息
     *
     * @param candidateId 主键
     * @return 候选人简历信息
     */
    @GetMapping("findResumeByCandidateId")
    public String findResumeByCandidateId(@RequestParam String candidateId) {
        return resumeService.findResumeByCandidateId(candidateId);
    }

    /**
     * 通过主键查找候选人信息
     *
     * @param id 主键
     * @return 候选人信息
     */
    @GetMapping("findById")
    public CandidateVO findById(@RequestParam Integer id) {
        return candidateService.findById(id);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @GetMapping("deleteById")
    public boolean deleteById(Integer id) {
        candidateService.deleteById(id);
        return true;
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryCandidatePage")
    public Page<CandidateVO> queryCandidatePage(String search, Integer currentPage, Integer pageSize) {
        return candidateService.queryCandidatePage(search, currentPage, pageSize);
    }

    /**
     * 查询候选人
     *
     * @param search 搜索关键字
     * @return
     */
    @GetMapping("queryCandidate")
    public List<CandidateVO> queryCandidate(@NotEmpty String search) {
        search = "%" + search + "%";
        return candidateService.queryCandidate(search);
    }
}

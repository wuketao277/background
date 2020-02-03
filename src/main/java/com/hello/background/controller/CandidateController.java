package com.hello.background.controller;

import com.alibaba.fastjson.JSONObject;
import com.hello.background.service.CandidateService;
import com.hello.background.vo.CandidateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public CandidateVO save(@RequestBody CandidateVO vo) {
        return candidateService.save(vo);
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
        search = "%" + search + "%";
        return candidateService.queryCandidatePage(search, currentPage, pageSize);
    }
}

package com.hello.background.controller;

import com.hello.background.service.CaseService;
import com.hello.background.vo.CaseVO;
import com.hello.background.vo.UserVO;
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
}

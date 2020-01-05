package com.hello.background.controller;

import com.hello.background.service.CaseService;
import com.hello.background.vo.ClientCaseVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * @author wuketao
 * @date 2019/12/29
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("case")
public class ClientCaseController {
    @Autowired
    private CaseService caseService;

    @PostMapping("save")
    public ClientCaseVO save(@RequestBody ClientCaseVO vo, HttpSession session) {
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
    public Page<ClientCaseVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        return caseService.queryPage(search, currentPage, pageSize);
    }
}

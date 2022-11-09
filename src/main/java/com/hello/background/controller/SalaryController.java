package com.hello.background.controller;

import com.hello.background.repository.UserRoleRepository;
import com.hello.background.service.SalaryService;
import com.hello.background.vo.GenerateSalaryRequest;
import com.hello.background.vo.SalaryInfoVO;
import com.hello.background.vo.SalaryVO;
import com.hello.background.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 工资控制器
 *
 * @author wuketao
 * @date 2021/10/15
 * @Description
 */
@RestController
@RequestMapping("salary")
public class SalaryController {
    @Autowired
    private SalaryService salaryService;
    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * 生成工资
     *
     * @return
     */
    @PostMapping("generateSalary")
    public void generateSalary(@RequestBody GenerateSalaryRequest request, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        salaryService.generateSalary(request.getMonth(), user.getUsername());
    }

    /**
     * 更新工资
     *
     * @return
     */
    @PostMapping("update")
    public boolean update(@RequestBody SalaryVO vo, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        return salaryService.update(vo, user);
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
    public SalaryInfoVO queryPage(String search, Integer currentPage, Integer pageSize, HttpSession session) {
        return salaryService.queryPage(session, search, currentPage, pageSize);
    }

    /**
     * 下载薪资
     *
     * @return
     */
    @GetMapping("downloadSalary")
    public void downloadSalary(@RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize, @RequestParam("search") String search, HttpSession session, HttpServletResponse response) {
        salaryService.downloadSalary(currentPage, pageSize, search, session, response);
    }
}

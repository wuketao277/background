package com.hello.background.controller;

import com.hello.background.repository.UserRoleRepository;
import com.hello.background.service.SalaryService;
import com.hello.background.vo.GenerateSalaryRequest;
import com.hello.background.vo.SalaryInfoVO;
import com.hello.background.vo.SalaryVO;
import com.hello.background.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    public Page<SalaryVO> queryPage(String search, Integer currentPage, Integer pageSize, HttpSession session) {
        return salaryService.queryPage(session, search, currentPage, pageSize);
    }

    /**
     * 获取薪资统计信息
     *
     * @return
     */
    @GetMapping("getSalaryStatisticsInfo")
    public SalaryInfoVO getSalaryStatisticsInfo() {
        return salaryService.getSalaryStatisticsInfo();
    }
}

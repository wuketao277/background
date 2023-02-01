package com.hello.background.controller;

import com.hello.background.constant.RoleEnum;
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
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryPage")
    public SalaryInfoVO queryPage(@RequestParam(value = "loginName", required = false) String loginName,
                                  @RequestParam(value = "userName", required = false) String userName,
                                  @RequestParam(value = "month", required = false) String month,
                                  @RequestParam(value = "pretaxIncome", required = false) String pretaxIncome,
                                  @RequestParam(value = "netPay", required = false) String netPay,
                                  @RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize, HttpSession session) {
        return salaryService.queryPage(session, loginName, userName, month, pretaxIncome, netPay, currentPage, pageSize);
    }

    /**
     * 下载薪资
     *
     * @return
     */
    @GetMapping("downloadSalary")
    public void downloadSalary(@RequestParam(value = "loginName", required = false) String loginName,
                               @RequestParam(value = "userName", required = false) String userName,
                               @RequestParam(value = "month", required = false) String month,
                               @RequestParam(value = "pretaxIncome", required = false) String pretaxIncome,
                               @RequestParam(value = "netPay", required = false) String netPay,
                               @RequestParam("currentPage") Integer currentPage,
                               @RequestParam("pageSize") Integer pageSize,
                               HttpSession session,
                               HttpServletResponse response) {
        salaryService.downloadSalary(session, response, loginName, userName, month, pretaxIncome, netPay, currentPage, pageSize);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @GetMapping("deleteById")
    public boolean deleteById(Integer id, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        // 非管理员角色不能执行
        if (!user.getRoles().contains(RoleEnum.ADMIN)) {
            return false;
        }
        salaryService.deleteById(id);
        return true;
    }
}

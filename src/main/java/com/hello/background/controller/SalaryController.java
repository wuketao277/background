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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 通过客户和base计算billing和gp
     *
     * @param base
     * @param clientId
     * @return
     */
    @GetMapping("calcBillingByBaseAndClient")
    public List<BigDecimal> calcBillingByBaseAndClient(@RequestParam("base") BigDecimal base, @RequestParam("clientId") Integer clientId) {
        BigDecimal billing = BigDecimal.ZERO;
        BigDecimal gp = BigDecimal.ZERO;
        if (null != base && null != clientId) {
            switch (clientId) {
                case 6: // 沃尔沃汽车销售（上海）有限公司
                case 7: // 上海三菱电梯有限公司
                case 73488: // 上海三菱电梯有限公司安徽分公司
                case 8020: // 沃尔沃亚太-沃尔沃汽车技术（上海）有限公司
                case 36827: // 沃尔沃亚太-沃尔沃汽车（亚太）投资控股有限公司
                case 55711: // 大庆沃尔沃汽车制造有限公司
                case 65013: // 亚欧汽车制造（台州）有限公司
                    // 客户付税
                    billing = base.multiply(BigDecimal.valueOf(12)).multiply(BigDecimal.valueOf(0.2)).multiply(BigDecimal.valueOf(1.06));
                    break;
                case 3585: // 领悦数字信息技术有限公司
                case 3635: // 福特南京
                case 11682: // 福特汽车金融（中国）有限公司
                case 17479: // 宝马（中国）服务有限公司
                case 45646: // 宝马汽车金融（中国）有限公司
                case 46862: // 先锋国际融资租赁有限公司
                case 50812: // 领悦数字信息技术有限公司南京分公司
                case 84227: // 华晨宝马汽车有限公司北京分公司
                case 20047: // 福特汽车（中国）有限公司
                case 36998: // 福特汽车工程研究（南京）有限公司
                    // 我们付税
                    billing = base.multiply(BigDecimal.valueOf(12)).multiply(BigDecimal.valueOf(0.2));
                    break;
                case 10114: // 华晨宝马汽车有限公司沈阳
                    // 我们付税并且需要特殊计算
                    // 首先计算预付金额
                    billing = base.multiply(BigDecimal.valueOf(12)).multiply(BigDecimal.valueOf(0.2));
                    // 然后计算不含税的金额
                    billing = billing.divide(BigDecimal.valueOf(1.06), 0, RoundingMode.DOWN);
                    // 最后计算含税的金额
                    billing = billing.multiply(BigDecimal.valueOf(1.06));
                    break;

            }
            // 通过billing计算gp
            gp = billing.divide(BigDecimal.valueOf(1.06)).subtract(billing.subtract(billing.divide(BigDecimal.valueOf(1.06))).multiply(BigDecimal.valueOf(0.07)));
        }
        ArrayList<BigDecimal> list = new ArrayList<>();
        list.add(billing.setScale(2, RoundingMode.DOWN));
        list.add(gp.setScale(2, RoundingMode.DOWN));
        return list;
    }
}

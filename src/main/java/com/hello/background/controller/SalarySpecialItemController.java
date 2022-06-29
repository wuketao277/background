package com.hello.background.controller;

import com.hello.background.service.SalarySpecialItemService;
import com.hello.background.vo.SalarySpecialItemVO;
import com.hello.background.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 工资特殊项目控制器
 *
 * @author wuketao
 * @date 2020/2/3
 * @Description
 */
@RestController
@RequestMapping("salarySpecialItem")
public class SalarySpecialItemController {
    @Autowired
    private SalarySpecialItemService salarySpecialItemService;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public SalarySpecialItemVO save(@RequestBody SalarySpecialItemVO vo, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");
        vo.setUpdateTime(new Date());
        vo.setUpdateUserName(user.getUsername());
        return salarySpecialItemService.save(vo);
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
    public Page<SalarySpecialItemVO> queryPage(@NotEmpty String search, Integer currentPage, Integer pageSize, HttpSession session) {
        return salarySpecialItemService.queryPage(session, search, currentPage, pageSize);
    }

    /**
     * 通过主键删除报销
     *
     * @return
     */
    @DeleteMapping("deleteById")
    public void deleteById(@RequestParam Integer id) {
        salarySpecialItemService.deleteById(id);
    }
}

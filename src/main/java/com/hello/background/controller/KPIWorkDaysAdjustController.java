package com.hello.background.controller;

import com.hello.background.service.KPIWorkDaysAdjustService;
import com.hello.background.vo.KPIWorkDaysAdjustRequest;
import com.hello.background.vo.KPIWorkDaysAdjustVO;
import com.hello.background.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author wuketao
 * @date 2019/12/29
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("kpiWorkDaysAdjust")
public class KPIWorkDaysAdjustController {
    @Autowired
    private KPIWorkDaysAdjustService service;

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    @PostMapping("save")
    public KPIWorkDaysAdjustVO save(@RequestBody KPIWorkDaysAdjustVO vo) {
        return service.save(vo);
    }

    /**
     * 通过主键删除
     *
     * @return
     */
    @GetMapping("deleteById")
    public void deleteById(Integer id) {
        service.deleteById(id);
    }

    /**
     * 查询分页
     *
     * @return
     */
    @PostMapping("queryPage")
    public Page<KPIWorkDaysAdjustVO> queryPage(@RequestBody KPIWorkDaysAdjustRequest request, HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        return service.queryPage(request.getSearch(), request.getCurrentPage(), request.getPageSize(), userVO);
    }

    /**
     * 通过职位id查找
     *
     * @param id
     * @return
     */
    @GetMapping("queryById")
    public KPIWorkDaysAdjustVO queryById(Integer id) {
        return service.queryById(id);
    }
}

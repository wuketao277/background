package com.hello.background.controller;

import com.hello.background.service.KPIService;
import com.hello.background.vo.KPIVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuketao
 * @date 2019/12/14
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("kpi")
public class KPIController {

    @Autowired
    private KPIService kpiService;

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    @GetMapping("queryKPIVOPage")
    public Page<KPIVO> queryKPIVOPage(String search, Integer currentPage, Integer pageSize) {
        return kpiService.queryKPIVOPage(search, currentPage, pageSize);
    }
}

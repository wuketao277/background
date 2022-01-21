package com.hello.background.controller;

import com.hello.background.service.ReportService;
import com.hello.background.vo.QueryGeneralReportRequest;
import com.hello.background.vo.QueryGeneralReportResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报告
 *
 * @author wuketao
 * @date 2019/12/14
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 查询通用报表数据
     *
     * @return
     */
    @PostMapping("queryGeneral")
    public QueryGeneralReportResponse queryGeneral(@RequestBody QueryGeneralReportRequest request) {
        return reportService.queryGeneral(request);
    }

}

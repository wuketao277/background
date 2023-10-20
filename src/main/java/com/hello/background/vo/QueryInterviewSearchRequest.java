package com.hello.background.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author wuketao
 * @date 2023/10/16
 * @Description
 */
@Data
public class QueryInterviewSearchRequest {
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * title
     */
    private String title;
    /**
     * 开始时间
     */
    private LocalDate startDate;
    /**
     * 结束时间
     */
    private LocalDate endDate;
    /**
     * 阶段
     */
    private String phase;
    /**
     * 查看所有面试
     */
    private Boolean allInterview;
}

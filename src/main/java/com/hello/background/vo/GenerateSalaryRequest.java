package com.hello.background.vo;

import lombok.Data;

/**
 * 生成工资请求
 * @author wuketao
 * @date 2021/10/15
 * @Description
 */
@Data
public class GenerateSalaryRequest {
    /**
     * 月份
     */
    private String month;
}

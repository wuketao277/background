package com.hello.background.vo;

import lombok.Data;

/**
 * 生成报销请求
 * @author wuketao
 * @date 2022/10/30
 * @Description
 */
@Data
public class GenerateReimbursementRequest {
    /**
     * 月份
     */
    private String month;
}

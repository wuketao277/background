package com.hello.background.vo;

import com.hello.background.constant.InvoiceType;
import lombok.Data;

import java.util.Date;

/**
 * 发票分页查询
 *
 * @author wuketao
 * @date 2021/11/20
 * @Description
 */
@Data
public class InvoiceVOPageSearchRequest {
    /**
     * 客户id
     */
    private Integer clientId;

    /**
     * AM ID
     */
    private Integer userId;

    /**
     * 候选人 id
     */
    private Integer candidateId;

    /**
     * 发票类型
     */
    private InvoiceType type;

    /**
     * 正常/作废
     */
    private Boolean status;

    /**
     * 开票日期开始
     */
    private Date createDateStart;

    /**
     * 开票日期结束
     */
    private Date createDateEnd;
}

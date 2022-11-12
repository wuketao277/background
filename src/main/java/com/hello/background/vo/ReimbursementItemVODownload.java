package com.hello.background.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.hello.background.domain.ReimbursementItem;
import com.hello.background.utils.TransferUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author wuketao
 * @date 2021/11/28
 * @Description
 */
@Data
public class ReimbursementItemVODownload {
    /**
     * 报销公司名称
     */
    @ExcelProperty(value = "报销公司名称", index = 0)
    private String companyName;
    /**
     * 报销人登录名
     */
    @ExcelProperty(value = "登录名", index = 1)
    private String userName;
    /**
     * 报销发放月份
     */
    @ExcelProperty(value = "报销月", index = 2)
    private String paymentMonth;
    /**
     * 发生日期
     */
    @ExcelProperty(value = "发生日期", index = 3)
    private String date;
    /**
     * 发生地点
     */
    @ExcelProperty(value = "地点", index = 4)
    private String locationStr;
    /**
     * 是否报销
     */
    @ExcelProperty(value = "是否报销", index = 5)
    private String needPayStr;
    /**
     * 报销类别
     */
    @ExcelProperty(value = "类别", index = 6)
    private String typeStr;
    /**
     * 报销项目
     */
    @ExcelProperty(value = "项目", index = 7)
    private String kindStr;
    /**
     * 发票号
     */
    @ExcelProperty(value = "发票号", index = 8)
    private String invoiceNo;
    /**
     * 单价
     */
    @ExcelProperty(value = "单价", index = 9)
    private BigDecimal price;
    /**
     * 数量
     */
    @ExcelProperty(value = "数量", index = 10)
    private BigDecimal count;
    /**
     * 报销金额
     */
    @ExcelProperty(value = "金额", index = 11)
    private BigDecimal sum;
    /**
     * 审批状态
     */
    @ExcelProperty(value = "审批状态", index = 12)
    private String approveStatusStr;
    /**
     * 描述
     */
    @ExcelProperty(value = "描述", index = 13)
    private String description;

    public ReimbursementItemVODownload(ReimbursementItem vo) {
        TransferUtil.transfer(vo, this);
        this.setApproveStatusStr(Optional.ofNullable(vo.getApproveStatus()).map(x -> x.toString()).orElse(""));
        this.setKindStr(Optional.ofNullable(vo.getKind()).map(x -> x.getName()).orElse(""));
        this.setNeedPayStr(Optional.ofNullable(vo.getNeedPay()).map(x -> x.getName()).orElse(""));
        this.setTypeStr(Optional.ofNullable(vo.getType()).map(x -> x.getName()).orElse(""));
        this.setCompanyName(Optional.ofNullable(vo.getCompany()).map(x -> x.getName()).orElse(""));
        this.setLocationStr(Optional.ofNullable(vo.getLocation()).map(x -> x.getName()).orElse(""));
    }
}

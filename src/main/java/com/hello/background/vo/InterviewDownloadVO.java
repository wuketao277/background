package com.hello.background.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 候选人面试
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewDownloadVO {
    /**
     * 主键id
     */
    @ExcelProperty(value = "id")
    private Integer id;

    /**
     * 客户公司名称
     */
    @ExcelProperty(value = "客户公司名称")
    private String clientName;

    /**
     * 职位名称
     */
    @ExcelProperty(value = "职位名称")
    private String caseTitle;

    /**
     * 候选人姓名
     */
    @ExcelProperty(value = "候选人姓名")
    private String candidateName;

    /**
     * 阶段
     */
    @ExcelProperty(value = "阶段")
    private String phase;

    /**
     * 面试时间
     */
    @ExcelProperty(value = "面试时间")
    private String interviewTimeStr;

    /**
     * 录入时间
     */
    @ExcelProperty(value = "录入时间")
    private String inputTimeStr;

    /**
     * 内容
     */
    @ExcelProperty(value = "内容")
    private String content;

    /**
     * 客户id
     */
    @ExcelProperty(value = "clientId")
    private Integer clientId;

    /**
     * 职位id
     */
    @ExcelProperty(value = "caseId")
    private Integer caseId;
    
    /**
     * 候选人id
     */
    @ExcelProperty(value = "candidateId")
    private Integer candidateId;

    /**
     * 录入人名称
     */
    @ExcelProperty(value = "username")
    private String username;

    /**
     * 真实姓名
     */
    @ExcelProperty(value = "realname")
    private String realname;

    /**
     * 是否是终面
     */
    @ExcelProperty(value = "isFinal")
    private Boolean isFinal;

    /**
     * 面试时间
     */
    @ExcelProperty(value = "interviewTime")
    private Date interviewTime;

    /**
     * 录入时间
     */
    @ExcelProperty(value = "inputTime")
    private Date inputTime;
}

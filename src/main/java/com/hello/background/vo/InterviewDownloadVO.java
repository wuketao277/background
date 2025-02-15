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
     * 候选人id
     */
    @ExcelProperty(value = "candidateId")
    private Integer candidateId;

    /**
     * 候选人姓名
     */
    @ExcelProperty(value = "candidateName")
    private String candidateName;

    /**
     * 客户id
     */
    @ExcelProperty(value = "clientId")
    private Integer clientId;

    /**
     * 客户公司名称
     */
    @ExcelProperty(value = "clientName")
    private String clientName;

    /**
     * 职位id
     */
    @ExcelProperty(value = "caseId")
    private Integer caseId;

    /**
     * 职位名称
     */
    @ExcelProperty(value = "caseTitle")
    private String caseTitle;

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
     * 阶段
     */
    @ExcelProperty(value = "phase")
    private String phase;

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
     * 面试时间
     */
    @ExcelProperty(value = "interviewTimeStr")
    private String interviewTimeStr;

    /**
     * 内容
     */
    @ExcelProperty(value = "content")
    private String content;

    /**
     * 录入时间
     */
    @ExcelProperty(value = "inputTime")
    private Date inputTime;

    /**
     * 录入时间
     */
    @ExcelProperty(value = "inputTimeStr")
    private String inputTimeStr;
}

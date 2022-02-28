package com.hello.background.vo;

import com.hello.background.constant.CaseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 职位
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseVO {
    /**
     * 职位主键id
     */
    private Integer id;

    /**
     * 客户id
     */
    private Integer clientId;

    /**
     * 客户名称
     */
    private String clientChineseName;

    /**
     * 职位名称
     */
    private String title;

    /**
     * 职级
     */
    private String level;

    /**
     * 部门
     */
    private String department;

    /**
     * 汇报对象
     */
    private String lineManager;

    /**
     * 是否带人
     */
    private String subordinates;

    /**
     * 英语要求
     */
    private String english;

    /**
     * 年龄要求
     */
    private String age;

    /**
     * 工作经验
     */
    private String experience;

    /**
     * 学历要求
     */
    private String school;

    /**
     * 薪资范围
     */
    private String salaryScope;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private CaseStatusEnum status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    private String createUserId;
}

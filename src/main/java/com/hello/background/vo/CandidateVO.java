package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 候选人
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateVO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 英文名字
     */
    private String englishName;

    /**
     * 中文名字
     */
    private String chineseName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 电话
     */
    private String phoneNo;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 部门
     */
    private String department;

    /**
     * 职位名称
     */
    private String title;
}

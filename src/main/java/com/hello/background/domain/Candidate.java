package com.hello.background.domain;

import com.hello.background.constant.CandidateNotMatchReasonEnum;
import com.hello.background.constant.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
@Entity
public class Candidate {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 日期
     */
    @Column(length = 20)
    private String date;

    /**
     * 中文名字
     */
    @Column(length = 50)
    private String chineseName;

    /**
     * 英文名字
     */
    @Column(length = 100)
    private String englishName;

    /**
     * 年龄
     */
    @Column
    private Integer age;

    /**
     * 生日
     */
    @Column(length = 20)
    private String birthDay;

    /**
     * 电话
     */
    @Column(length = 20)
    private String phoneNo;

    /**
     * 邮箱
     */
    @Column(length = 200)
    private String email;

    /**
     * 性别
     */
    @Enumerated
    private GenderEnum gender;

    /**
     * 公司名称
     */
    @Column(length = 200)
    private String companyName;

    /**
     * 部门
     */
    @Column(length = 200)
    private String department;

    /**
     * 职位名称
     */
    @Column(length = 200)
    private String title;

    /**
     * 学校名称
     */
    @Column(length = 200)
    private String schoolName;

    /**
     * 户籍地址
     */
    @Column(length = 100)
    private String hometown;

    /**
     * 现地址
     */
    @Column(length = 100)
    private String currentAddress;

    /**
     * 期望地址
     */
    @Column(length = 100)
    private String futureAddress;

    /**
     * 现薪资
     */
    @Column(length = 100)
    private String currentMoney;

    /**
     * 期望薪资
     */
    @Column(length = 100)
    private String futureMoney;

    /**
     * 备注
     */
    @Column(length = 2000)
    private String remark;

    /**
     * 英文水平
     */
    @Column(length = 200)
    private String englishLevel;

    /**
     * 日文水平
     */
    @Column(length = 200)
    private String japaneseLevel;

    /**
     * 候选人不匹配原因
     */
    @Column
    @Enumerated(value = EnumType.STRING)
    private CandidateNotMatchReasonEnum notMatchReason;

    /**
     * 候选人不匹配的详细原因
     */
    @Column(length = 400)
    private String notMatchReasonDetail;

    /**
     * 创建用户ID
     */
    @Column
    private Integer createUserId;

    /**
     * 创建用户登录名
     */
    @Column(length = 50)
    private String createUserName;

    /**
     * 创建用户真实姓名
     */
    @Column(length = 50)
    private String createRealName;
}

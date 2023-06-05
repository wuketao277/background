package com.hello.background.domain;

import com.hello.background.constant.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * PRC
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PRC {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

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
     * 生日
     */
    @Column(length = 20)
    private String birthDay;

    /**
     * 星座
     */
    @Column(length = 20)
    private String constellation;

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
     * 英文水平
     */
    @Column(length = 200)
    private String englishLevel;

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
     * 家庭情况
     */
    @Column(length = 200)
    private String family;

    /**
     * 公司名称
     */
    @Column(length = 200)
    private String companyName;

    /**
     * 职位名称
     */
    @Column(length = 200)
    private String title;

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
     * 学校名称
     */
    @Column(length = 200)
    private String schoolName;

    /**
     * 备注
     */
    @Column(length = 2000)
    private String remark;
}

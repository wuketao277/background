package com.hello.background.vo;

import com.hello.background.constant.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class PRCVO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 中文名字
     */
    private String chineseName;

    /**
     * 英文名字
     */
    private String englishName;

    /**
     * 生日
     */
    private String birthDay;

    /**
     * 星座
     */
    private String constellation;

    /**
     * 电话
     */
    private String phoneNo;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private GenderEnum gender;

    /**
     * 英语水平
     */
    private String englishLevel;

    /**
     * 户籍地址
     */
    private String hometown;

    /**
     * 现地址
     */
    private String currentAddress;

    /**
     * 期望地址
     */
    private String futureAddress;

    /**
     * 家庭情况
     */
    private String family;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 职位名称
     */
    private String title;

    /**
     * 现薪资
     */
    private String currentMoney;

    /**
     * 期望薪资
     */
    private String futureMoney;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 备注
     */
    private String remark;
}

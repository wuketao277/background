package com.hello.background.vo;

import com.hello.background.constant.CandidateDoubleCheckEnum;
import com.hello.background.constant.CandidateNotMatchReasonEnum;
import com.hello.background.constant.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
     * 日期
     */
    private String date;

    /**
     * 中文名字
     */
    private String chineseName;

    /**
     * 英文名字
     */
    private String englishName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 生日
     */
    private String birthDay;

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
     * 日文水平
     */
    private String japaneseLevel;

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
     * 部门
     */
    private String department;

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
     * 公司架构
     */
    private String companyStructure;

    /**
     * 面试历史
     */
    private String interviewHistory;

    /**
     * 求职动机
     */
    private String motivation;

    /**
     * 离职分析
     */
    private String dimissionAnalysis;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 候选人不匹配原因
     */
    private CandidateNotMatchReasonEnum notMatchReason;

    /**
     * 候选人不匹配的详细原因
     */
    private String notMatchReasonDetail;

    /**
     * 创建用户ID
     */
    private Integer createUserId;

    /**
     * 创建用户登录名
     */
    private String createUserName;

    /**
     * 创建用户真实姓名
     */
    private String createRealName;

    /**
     * 必要检查
     */
    private List<CandidateDoubleCheckEnum> doubleCheck;
}

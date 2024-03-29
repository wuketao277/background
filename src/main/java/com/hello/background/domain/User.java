package com.hello.background.domain;

import com.hello.background.constant.*;
import com.hello.background.converter.RoleEnumListStringAttrConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户信息表
 *
 * @author wuketao
 * @date 2019/10/23
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 登录名
     */
    @Column(length = 50, nullable = false)
    private String username;
    /**
     * 真实姓名
     */
    @Column(length = 50, nullable = false)
    private String realname;
    /**
     * 所属公司
     */
    @Column
    @Enumerated(value = EnumType.STRING)
    private CompanyEnum company;
    /**
     * 密码
     */
    @Column(length = 50, nullable = false)
    private String password;
    /**
     * 工作类型
     */
    @Enumerated
    private JobTypeEnum jobType;
    /**
     * 客户公司ID，只有外包员工才有该数据
     */
    @Column
    private Integer clientCompanyId;
    /**
     * 底薪
     */
    @Column
    private BigDecimal salarybase;
    /**
     * 是否cover base
     */
    @Column
    private Boolean coverbase;
    /**
     * 入职日期
     */
    @Column
    private Date onBoardDate;
    /**
     * 离职日期
     */
    @Column
    private Date dimissionDate;
    /**
     * 是否考核KPI
     */
    @Column
    private Boolean checkKPI;
    /**
     * 角色
     */
    @Column(length = 100)
    @Convert(converter = RoleEnumListStringAttrConverter.class)
    private List<RoleEnum> roles;
    /**
     * 剩余事假
     */
    @Column
    private BigDecimal remainHolidayThing;
    /**
     * 剩余病假
     */
    @Column
    private BigDecimal remainHolidayIll;
    /**
     * 创建日期
     */
    @Column(length = 50, nullable = false)
    private Date createDate;
    /**
     * 最后登录时间
     */
    @Column
    private Date lastLoginTime;
    /**
     * 是否可用
     */
    @Column
    private Boolean enabled;
    /**
     * 是否过期
     */
    @Column
    private boolean accountNonExpired;
    /**
     * 是否锁定
     */
    @Column
    private boolean accountNonLocked;
    /**
     * 证书是否过期
     */
    @Column
    private boolean credentialsNonExpired;
    /**
     * 性别
     */
    @Enumerated
    private GenderEnum gender;
    /**
     * 手机号
     */
    @Column(length = 15)
    private String phoneNo;
    /**
     * 手机号2
     */
    @Column(length = 15)
    private String phoneNo2;
    /**
     * 手机号3
     */
    @Column(length = 15)
    private String phoneNo3;
    /**
     * 邮箱地址
     */
    @Column(length = 50)
    private String email;
    /**
     * 工作地址
     */
    @Column(length = 200)
    private String workAddress;
    /**
     * 居住地址
     */
    @Column(length = 200)
    private String lifeAddress;
    /**
     * 户籍地址
     */
    @Column(length = 200)
    private String homeAddress;
    /**
     * 工资卡银行
     */
    @Enumerated
    private BankEnum bank;
    /**
     * 工资卡开户银行名称
     */
    @Column(length = 200)
    private String cardBankName;
    /**
     * 工资卡号
     */
    @Column(length = 50)
    private String cardNumber;
    /**
     * 公积金账号
     */
    @Column(length = 50)
    private String gongJiJinAccount;
    /**
     * 身份证号码
     */
    @Column(length = 20)
    private String idCardNo;
    /**
     * 生日
     */
    @Column
    private Date birthday;
    /**
     * 教育背景
     */
    @Column(length = 100)
    private String educationBackground;
    /**
     * 紧急联系人
     */
    @Column(length = 20)
    private String emergencyContact;
    /**
     * 紧急联系电话
     */
    @Column(length = 15)
    private String emergencyTelephoneNo;
    /**
     * 团队leader登录名
     */
    @Column(length = 50)
    private String teamLeaderUserName;
}
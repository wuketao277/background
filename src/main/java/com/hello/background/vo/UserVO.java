package com.hello.background.vo;

import com.hello.background.constant.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户 视图对象
 *
 * @author wuketao
 * @date 2019/12/7
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {
    /**
     * 用户ID
     */
    private Integer id;
    /**
     * 登录名
     */
    private String username;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * 公司
     */
    private CompanyEnum company;
    /**
     * 密码
     */
    private String password;
    /**
     * 工作类型
     */
    private JobTypeEnum jobType;
    /**
     * 客户公司ID，只有外包员工才有该数据
     */
    private Integer clientCompanyId;
    /**
     * 底薪
     */
    private BigDecimal salarybase;
    /**
     * 是否cover base
     */
    private Boolean coverbase;
    /**
     * 入职日期
     */
    private Date onBoardDate;
    /**
     * 离职日期
     */
    private Date dimissionDate;
    /**
     * 是否考核KPI
     */
    private Boolean checkKPI;
    /**
     * 角色
     */
    private List<RoleEnum> roles;
    /**
     * 剩余事假
     */
    private BigDecimal remainHolidayThing;
    /**
     * 剩余病假
     */
    private BigDecimal remainHolidayIll;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    /**
     * 是否启用
     */
    private Boolean enabled;
    /**
     * 是否启用
     */
    private String enabledName;
    /**
     * 是否过期
     */
    private boolean accountNonExpired;
    /**
     * 是否锁定
     */
    private boolean accountNonLocked;
    /**
     * 证书是否过期
     */
    private boolean credentialsNonExpired;
    /**
     * 性别
     */
    private GenderEnum gender;
    /**
     * 手机号
     */
    private String phoneNo;
    /**
     * 手机号2
     */
    private String phoneNo2;
    /**
     * 手机号3
     */
    private String phoneNo3;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 工作地址
     */
    private String workAddress;
    /**
     * 居住地址
     */
    private String lifeAddress;
    /**
     * 户籍地址
     */
    private String homeAddress;
    /**
     * 工资卡银行
     */
    private BankEnum bank;
    /**
     * 工资卡开户银行名称
     */
    private String cardBankName;
    /**
     * 工资卡号
     */
    private String cardNumber;
    /**
     * 公积金账号
     */
    private String gongJiJinAccount;
    /**
     * 身份证号码
     */
    private String idCardNo;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 户籍类型
     */
    private String typeOfResidence;
    /**
     * 教育背景
     */
    private String educationBackground;
    /**
     * 紧急联系人
     */
    private String emergencyContact;
    /**
     * 紧急联系电话
     */
    private String emergencyTelephoneNo;
    /**
     * 团队leader登录名
     */
    private String teamLeaderUserName;
}

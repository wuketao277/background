package com.hello.background.domain;

import com.hello.background.constant.HolidayApproveStatusEnum;
import com.hello.background.constant.HolidayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 假期
 *
 * @author wuketao
 * @date 2019/12/7
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Holiday {
    /**
     * 唯一标识
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 请假人ID
     */
    @Column
    private Integer userId;
    /**
     * 请假人登录名
     */
    @Column(length = 50)
    private String userName;
    /**
     * 请假人真实姓名
     */
    @Column(length = 50)
    private String userRealName;
    /**
     * 请假日期
     */
    @Column
    private Date holidayDate;
    /**
     * 请假时长
     */
    @Column
    private BigDecimal holidayLong;
    /**
     * 备注
     */
    @Column(length = 200)
    private String remark;
    /**
     * 假期申请审批状态
     */
    @Enumerated
    private HolidayApproveStatusEnum approveStatus;
    /**
     * 请假类型
     */
    @Enumerated
    private HolidayTypeEnum holidayType;
    /**
     * 审批人ID
     */
    @Column
    private Integer approveUserId;
    /**
     * 审批人登录名
     */
    @Column(length = 50)
    private String approveUserName;
    /**
     * 审批人真实姓名
     */
    @Column(length = 50)
    private String approveUserRealName;
}

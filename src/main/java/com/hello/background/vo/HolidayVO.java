package com.hello.background.vo;

import com.hello.background.constant.HolidayApproveStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 假期
 *
 * @author wuketao
 * @date 2019/12/7
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayVO {
    /**
     * 唯一标识
     */
    private Integer id;
    /**
     * 请假人ID
     */
    private Integer userId;
    /**
     * 请假人登录名
     */
    private String userName;
    /**
     * 请假人真实姓名
     */
    private String userRealName;
    /**
     * 请假日期
     */
    private Date holidayDate;
    /**
     * 请假时长
     */
    private BigDecimal holidayLong;
    /**
     * 备注
     */
    private String remark;
    /**
     * 假期申请审批状态
     */
    private HolidayApproveStatusEnum approveStatus;
    /**
     * 审批人ID
     */
    private Integer approveUserId;
    /**
     * 审批人登录名
     */
    private String approveUserName;
    /**
     * 审批人真实姓名
     */
    private String approveUserRealName;
}

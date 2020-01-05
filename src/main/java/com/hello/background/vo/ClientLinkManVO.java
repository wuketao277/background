package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wuketao
 * @date 2019/12/28
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientLinkManVO {
    /**
     * 新闻主键id
     */
    private Integer id;

    /**
     * 客户id
     */
    private Integer clientId;

    /**
     * 客户姓名
     */
    private String clientName;

    /**
     * 联系人中文名
     */
    private String chineseName;

    /**
     * 联系人英文名
     */
    private String englishName;

    /**
     * 联系人地址
     */
    private String address;

    /**
     * 联系人邮箱
     */
    private String email;

    /**
     * 联系人手机号
     */
    private String mobileNo;

    /**
     * 联系人固话号
     */
    private String phoneNo;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    private String createUserId;
}

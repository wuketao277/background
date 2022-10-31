package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author wuketao
 * @date 2019/12/28
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClientLinkMan {
    /**
     * 新闻主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 客户id
     */
    @Column
    private Integer clientId;

    /**
     * 联系人中文名
     */
    @Column(length = 200)
    private String chineseName;

    /**
     * 联系人英文名
     */
    @Column(length = 200)
    private String englishName;

    /**
     * 联系人地址
     */
    @Column(length = 200)
    private String address;

    /**
     * 联系人邮箱
     */
    @Column(length = 200)
    private String email;

    /**
     * 联系人手机号
     */
    @Column(length = 20)
    private String mobileNo;

    /**
     * 联系人固话号
     */
    @Column(length = 20)
    private String phoneNo;

    /**
     * 创建时间
     */
    @Column
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    @Column(length = 50)
    private String createUserId;

    /**
     * 备注
     */
    @Column(length = 400)
    private String comments;
}

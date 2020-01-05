package com.hello.background.domain;

import com.hello.background.constant.CaseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 案件
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClientCase {
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
     * 职位名称
     */
    @Column(length = 200)
    private String title;

    /**
     * 描述
     */
    @Column(length = 2000)
    private String description;

    /**
     * 状态
     */
    @Enumerated
    private CaseStatusEnum status;

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
}

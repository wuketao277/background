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
 * PRC评论
 *
 * @author wuketao
 * @date 2025/12/23
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PRCComment {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * PRC id
     */
    @Column
    private Integer prcId;

    /**
     * 登录名
     */
    @Column(length = 50)
    private String username;

    /**
     * 内容
     */
    @Column(length = 2000)
    private String content;

    /**
     * 录入时间
     */
    private LocalDateTime inputTime;
}

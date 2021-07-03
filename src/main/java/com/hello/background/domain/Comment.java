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
 * 候选人评论
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
public class Comment {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 后续人id
     */
    private Integer candidateId;

    /**
     * 登录名
     */
    @Column(length = 50)
    private String username;

    /**
     * 本名
     */
    @Column(length = 50)
    private String realname;

    /**
     * 阶段
     */
    @Column(length = 20)
    private String phase;

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

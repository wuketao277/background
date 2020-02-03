package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 我的任务
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MyTask {

    /**
     * 任务主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 关联候选人id
     */
    @Column
    private Integer relativeCandidateId;

    /**
     * 任务的标题
     */
    @Column(length = 200, nullable = false)
    private String taskTitle;

    /**
     * 任务的执行人登录名
     */
    @Column(length = 50, nullable = false)
    private String executeUserName;

    /**
     * 任务的执行人真实姓名
     */
    @Column(length = 50, nullable = false)
    private String executeRealName;

    /**
     * 任务的执行日期
     */
    @Column(nullable = false)
    private LocalDate executeDate;

    /**
     * 任务的内容
     */
    @Column(length = 2000, nullable = false)
    private String taskContent;

    /**
     * 任务的创建人登录名
     */
    @Column(length = 50, nullable = false)
    private String createUserName;

    /**
     * 任务的创建人真实姓名
     */
    @Column(length = 50, nullable = false)
    private String createRealName;

    /**
     * 任务的创建时间
     */
    @Column(nullable = false)
    private LocalDateTime createDateTime;
}

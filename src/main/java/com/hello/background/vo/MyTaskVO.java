package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 我的任务
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyTaskVO {

    /**
     * 任务主键id
     */
    private Integer id;

    /**
     * 关联候选人id
     */
    private Integer relativeCandidateId;

    /**
     * 任务的标题
     */
    private String taskTitle;

    /**
     * 任务的执行人登录名
     */
    private String executeUserName;

    /**
     * 任务的执行人真实姓名
     */
    private String executeRealName;

    /**
     * 任务的执行日期
     */
    private LocalDate executeDate;

    /**
     * 任务的内容
     */
    private String taskContent;

    /**
     * 任务的创建人登录姓名
     */
    private String createUserName;

    /**
     * 任务的创建人真实姓名
     */
    private String createRealName;

    /**
     * 任务的时间
     */
    private LocalDateTime createDateTime;
}

package com.hello.background.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 我的任务
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
public class MyTask {

    /**
     * 任务主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 任务的标题
     */
    @Column(length = 200, nullable = false)
    private String taskTitle;
}

package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Column;

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
     * 任务的标题
     */
    @Column(length = 200, nullable = false)
    private String taskTitle;
}

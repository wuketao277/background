package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 课程问题
 *
 * @author wuketao
 * @date 2022/8/3
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LessonQuestion {
    /**
     * 课程问题主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 课程id
     */
    @Column
    private Integer lessonId;

    /**
     * 课程名称
     */
    @Column(length = 200, nullable = false)
    private String lessonName;

    /**
     * 问题内容
     */
    @Column(length = 4000)
    private String lessonQuestionContent;
}

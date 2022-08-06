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
 * 课程
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
public class Lesson {
    /**
     * 职位主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 课程名称
     */
    @Column(length = 200, nullable = false)
    private String lessonName;

    /**
     * 课程内容
     */
    @Column(length = 4000)
    private String lessonContent;

    /**
     * 讲师id
     */
    @Column
    private Integer teacherId;

    /**
     * 讲师登录名
     */
    @Column(length = 50)
    private String teacherUserName;

    /**
     * 讲师真实姓名
     */
    @Column(length = 50)
    private String teacherRealName;
}

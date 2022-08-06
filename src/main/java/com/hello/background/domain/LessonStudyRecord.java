package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程学习记录
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
public class LessonStudyRecord {
    /**
     * 记录id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 课程id
     */
    @Column(nullable = false)
    private Integer lessonId;

    /**
     * 课程名称
     */
    @Column(length = 200, nullable = false)
    private String lessonName;

    /**
     * 登录名
     */
    @Column(length = 50, nullable = false)
    private String username;

    /**
     * 真实姓名
     */
    @Column(length = 50, nullable = false)
    private String realname;

    /**
     * 签到时间
     */
    @Column
    private LocalDateTime signInTime;

    /**
     * 学习完成时间
     */
    @Column
    private LocalDateTime finishTime;

    /**
     * 学习时长
     */
    @Column
    private BigDecimal hours;

    /**
     * 课程得分
     */
    @Column
    private Integer score;
}

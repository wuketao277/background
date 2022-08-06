package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程学习记录
 *
 * @author wuketao
 * @date 2022/8/3
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonStudyRecordVO {
    /**
     * 记录id
     */
    private Integer id;

    /**
     * 课程id
     */
    private Integer lessonId;

    /**
     * 课程名称
     */
    private String lessonName;

    /**
     * 登录名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 签到时间
     */
    private LocalDateTime signInTime;

    /**
     * 学习完成时间
     */
    private LocalDateTime finishTime;

    /**
     * 学习时长
     */
    private BigDecimal hours;

    /**
     * 课程得分
     */
    private Integer score;
}

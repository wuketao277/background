package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程问题
 *
 * @author wuketao
 * @date 2022/8/3
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonQuestionVO {
    /**
     * 课程问题主键id
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
     * 问题内容
     */
    private String lessonQuestionContent;
}

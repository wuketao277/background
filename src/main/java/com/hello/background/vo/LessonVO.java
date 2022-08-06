package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程
 *
 * @author wuketao
 * @date 2022/8/3
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonVO {
    /**
     * 职位主键id
     */
    private Integer id;

    /**
     * 课程名称
     */
    private String lessonName;

    /**
     * 课程内容
     */
    private String lessonContent;

    /**
     * 讲师id
     */
    private Integer teacherId;

    /**
     * 讲师登录名
     */
    private String teacherUserName;

    /**
     * 讲师真实姓名
     */
    private String teacherRealName;
}

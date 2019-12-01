package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 新闻领域对象视图对象
 *
 * @author wuketao
 * @date 2019/11/24
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyNewsVO {
    /**
     * 新闻主键id
     */
    private Integer id;

    /**
     * 新闻优先级，1~5，1最低，5最高
     */
    private String priority;

    /**
     * 新闻标题
     * 最多200个字符，不允许为空
     */
    private String title;

    /**
     * 新闻内容
     * 最多4000个字符
     */
    private String content;

    /**
     * 新闻链接
     * 最多500个字符
     */
    private String link;

    /**
     * 发布状态：true表示发布，false表示不发布
     */
    private Boolean publish;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}

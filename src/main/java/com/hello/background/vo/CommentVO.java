package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 候选人评论
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 后续人id
     */
    private Integer candidateId;

    /**
     * 录入人名称
     */
    private String inputUserName;

    /**
     * 阶段
     */
    private String phase;

    /**
     * 内容
     */
    private String content;

    /**
     * 录入时间
     */
    private LocalDateTime inputTime;
}

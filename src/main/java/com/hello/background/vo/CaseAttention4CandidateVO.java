package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 关注职位 候选人信息
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseAttention4CandidateVO {
    /**
     * 候选人id
     */
    private Integer candidateId;

    /**
     * 候选人姓名
     */
    private String candidateChineseName;

    /**
     * 最后评论人
     */
    private String latestCommentUsername;

    /**
     * 最后评论内容
     */
    private String latestCommentContent;

    /**
     * 最后评论时间
     */
    private LocalDateTime latestCommentInputtime;
}

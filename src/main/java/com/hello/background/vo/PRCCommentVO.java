package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * PRC评论
 *
 * @author wuketao
 * @date 2025/12/23
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PRCCommentVO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * PRC id
     */
    private Integer prcId;

    /**
     * 登录名
     */
    private String username;

    /**
     * 内容
     */
    private String content;

    /**
     * 录入时间
     */
    private LocalDateTime inputTime;
}

package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * 候选人 简历
 *
 * @author wuketao
 * @date 2021/02/16
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeVO {
    /**
     * 候选人id
     */
    private Integer candidateId;

    /**
     * 简历内容
     */
    @NotEmpty
    private String content;
}

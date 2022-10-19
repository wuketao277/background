package com.hello.background.vo;

import lombok.Data;

/**
 * @author wuketao
 * @date 2022/10/19
 * @Description
 */
@Data
public class UpdateCandidateAttentionRequest {
    private Boolean attention;
    private Integer candidateId;
}

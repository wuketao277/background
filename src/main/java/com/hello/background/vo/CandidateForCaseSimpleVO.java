package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 候选人与职位对应表
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateForCaseSimpleVO {
    /**
     * 候选人id
     */
    private Integer candidateId;

    /**
     * 职位id
     */
    private Integer caseId;

}

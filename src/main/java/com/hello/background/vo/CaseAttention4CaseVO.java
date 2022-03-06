package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注职位 职位信息
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseAttention4CaseVO {
    /**
     * 职位id
     */
    private Integer caseId;

    /**
     * 职位名称
     */
    private String caseTitle;

    /**
     * 候选人信息
     */
    private List<CaseAttention4CandidateVO> candidateList = new ArrayList<>();
}

package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * pipeline职位
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PipelineCaseVO {
    /**
     * 职位主键id
     */
    private Integer id;

    /**
     * 客户id
     */
    private Integer clientId;

    /**
     * 客户名称
     */
    private String clientChineseName;

    /**
     * 职位名称
     */
    private String title;

    /**
     * 已付款候选人列表
     */
    private List<PipelineCandidateVO> paymentCandidateList = new ArrayList<>();

    /**
     * 已开票未付款的候选人列表
     */
    private List<PipelineCandidateVO> invoiceCandidateList = new ArrayList<>();

    /**
     * 已入职未开票的候选人列表
     */
    private List<PipelineCandidateVO> onboardCandidateList = new ArrayList<>();

    /**
     * 已回签未入职的候选人列表
     */
    private List<PipelineCandidateVO> offerCandidateList = new ArrayList<>();

    /**
     * 面试阶段的候选人列表
     */
    private List<PipelineCandidateVO> interviewCandidateList = new ArrayList<>();

    /**
     * 有CVO但是还没有排面试的候选人列表
     */
    private List<PipelineCandidateVO> cvoCandidateList = new ArrayList<>();

    /**
     * 有VI/IVO，但是没有CVO的候选人列表
     */
    private List<PipelineCandidateVO> viioiCandidateList = new ArrayList<>();
}

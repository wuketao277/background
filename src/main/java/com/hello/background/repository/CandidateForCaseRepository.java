package com.hello.background.repository;

import com.hello.background.domain.CandidateForCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 候选人与职位对应表 仓库接口
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Repository
public interface CandidateForCaseRepository extends JpaRepository<CandidateForCase, Integer> {
    /**
     * 通过职位id获取所有推荐候选人信息
     *
     * @param caseId 职位id
     * @return 职位推荐候选人信息
     */
    List<CandidateForCase> findByCaseId(Integer caseId);

    /**
     * 通过职位id和关注状态获取推荐候选人信息
     *
     * @param caseId 职位id
     * @return 职位推荐候选人信息
     */
    List<CandidateForCase> findByCaseIdAndAttention(Integer caseId, Boolean attention);

    /**
     * 通过职位id和关注状态获取推荐候选人信息
     *
     * @param caseId 职位id
     * @return 职位推荐候选人信息
     */
    List<CandidateForCase> findByCaseIdAndAttentionOrderByCandidateIdDesc(Integer caseId, Boolean attention);

    /**
     * 通过候选人id、职位id查询
     *
     * @param candidateId
     * @param caseId
     * @return
     */
    List<CandidateForCase> findByCandidateIdAndCaseId(Integer candidateId, Integer caseId);

    /**
     * 通过候选人id查询
     *
     * @param candidateId
     * @return
     */
    List<CandidateForCase> findByCandidateId(Integer candidateId);

    /**
     * 通过caseId删除
     *
     * @param caseId
     */
    void deleteByCaseId(Integer caseId);

    /**
     * 通过候选人id删除
     *
     * @param candidateId
     */
    void deleteByCandidateId(Integer candidateId);
}

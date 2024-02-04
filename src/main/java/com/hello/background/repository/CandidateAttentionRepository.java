package com.hello.background.repository;

import com.hello.background.domain.CandidateAttention;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Repository
public interface CandidateAttentionRepository extends PagingAndSortingRepository<CandidateAttention, Integer>, JpaSpecificationExecutor<CandidateAttention> {
    /**
     * 通过候选人id和用户id查询
     *
     * @param candidateId
     * @param userId
     * @return
     */
    List<CandidateAttention> findByCandidateIdAndUserId(Integer candidateId, Integer userId);

    /**
     * 通过候选人id查询
     *
     * @param candidateId
     * @return
     */
    List<CandidateAttention> findByCandidateId(Integer candidateId);

    /**
     * 通过候选人id和用户id删除
     *
     * @param candidateId
     * @param userId
     * @return
     */
    void deleteByCandidateIdAndUserId(Integer candidateId, Integer userId);

    /**
     * 通过候选人ID进行删除
     *
     * @param candidateId
     */
    void deleteByCandidateId(Integer candidateId);

    /**
     * 通过用户的关注情况
     *
     * @param userId
     * @return
     */
    List<CandidateAttention> findByUserId(Integer userId);
}

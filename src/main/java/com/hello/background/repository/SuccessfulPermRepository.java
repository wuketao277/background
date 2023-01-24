package com.hello.background.repository;

import com.hello.background.domain.SuccessfulPerm;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author wuketao
 * @date 2019/12/6
 * @Description
 */
@Repository
public interface SuccessfulPermRepository extends PagingAndSortingRepository<SuccessfulPerm, Integer>, JpaSpecificationExecutor<SuccessfulPerm> {
    /**
     * 通过审批状态和实际付款日期查询
     *
     * @return
     */
    List<SuccessfulPerm> findByApproveStatusAndCommissionDateBetween(String approveStatus, Date start, Date end);

    /**
     * 通过审批状态集合查询
     *
     * @param approveStatusList
     * @return
     */
    List<SuccessfulPerm> findByApproveStatusIn(List<String> approveStatusList);

    /**
     * 通过候选人Id和职位Id查询成功case
     *
     * @param candidateId
     * @param caseId
     * @return
     */
    List<SuccessfulPerm> findByCandidateIdAndCaseId(Integer candidateId, Integer caseId);
}

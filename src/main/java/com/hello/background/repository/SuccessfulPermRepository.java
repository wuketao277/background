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
}

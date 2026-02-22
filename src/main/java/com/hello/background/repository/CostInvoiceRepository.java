package com.hello.background.repository;

import com.hello.background.domain.CostInvoice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 发票
 *
 * @author wuketao
 * @date 2026/2/22
 * @Description
 */
@Repository
public interface CostInvoiceRepository extends PagingAndSortingRepository<CostInvoice, Integer>, JpaSpecificationExecutor<CostInvoice> {
    /**
     * 通过顾问id和审批状态查询成本发票
     *
     * @param consultantId
     * @param approveStatus
     * @return
     */
    List<CostInvoice> findAllByConsultantIdAndApproveStatus(Integer consultantId, String approveStatus);

    /**
     * 通过审批状态查询成本发票
     *
     * @param approveStatus
     * @return
     */
    List<CostInvoice> findAllByApproveStatus(String approveStatus);
}

package com.hello.background.repository;

import com.hello.background.domain.CostInvoiceUsed;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 成本发票使用情况
 *
 * @author wuketao
 * @date 2026/2/22
 * @Description
 */
@Repository
public interface CostInvoiceUsedRepository extends PagingAndSortingRepository<CostInvoiceUsed, Integer>, JpaSpecificationExecutor<CostInvoiceUsed> {

}

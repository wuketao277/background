package com.hello.background.repository;

import com.hello.background.domain.CostInvoice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 发票
 *
 * @author wuketao
 * @date 2026/2/22
 * @Description
 */
@Repository
public interface CostInvoiceRepository extends PagingAndSortingRepository<CostInvoice, Integer>, JpaSpecificationExecutor<CostInvoice> {
   
}

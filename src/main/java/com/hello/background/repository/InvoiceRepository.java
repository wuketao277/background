package com.hello.background.repository;

import com.hello.background.domain.Invoice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 发票
 *
 * @author wuketao
 * @date 2022/9/25
 * @Description
 */
@Repository
public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Integer>, JpaSpecificationExecutor<Invoice> {
}

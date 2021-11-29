package com.hello.background.repository;

import com.hello.background.domain.ReimbursementItem;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuketao
 * @date 2021/11/28
 * @Description
 */
@Repository
public interface ReimbursementItemRepository extends PagingAndSortingRepository<ReimbursementItem, Integer>, JpaSpecificationExecutor<ReimbursementItem> {
}

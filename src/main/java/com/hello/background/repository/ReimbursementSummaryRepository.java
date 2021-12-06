package com.hello.background.repository;

import com.hello.background.domain.ReimbursementSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuketao
 * @date 2021/11/28
 * @Description
 */
@Repository
public interface ReimbursementSummaryRepository extends PagingAndSortingRepository<ReimbursementSummary, Integer>, JpaSpecificationExecutor<ReimbursementSummary> {

    /**
     * 查找全部分页
     *
     * @param pageable
     * @return
     */
    Page<ReimbursementSummary> findByPaymentMonthIsNotNullOrderByUpdateTimeDesc(Pageable pageable);

    /**
     * 通过名称查找分页
     *
     * @param pageable
     * @return
     */
    Page<ReimbursementSummary> findByUserNameOrderByUpdateTimeDesc(String userName, Pageable pageable);

    /**
     * 通过名称计算总数
     *
     * @param userName
     * @return
     */
    int countByUserName(String userName);

    /**
     * 通过支付月份删除
     *
     * @param paymentMonth
     */
    void deleteByPaymentMonth(String paymentMonth);
}

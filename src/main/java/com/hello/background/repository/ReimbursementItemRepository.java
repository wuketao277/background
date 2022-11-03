package com.hello.background.repository;

import com.hello.background.constant.ReimbursementApproveStatusEnum;
import com.hello.background.constant.ReimbursementNeedPayEnum;
import com.hello.background.domain.ReimbursementItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuketao
 * @date 2021/11/28
 * @Description
 */
@Repository
public interface ReimbursementItemRepository extends PagingAndSortingRepository<ReimbursementItem, Integer>, JpaSpecificationExecutor<ReimbursementItem> {
    /**
     * 通过报销人查询分页
     *
     * @param userName
     * @param pageable
     * @return
     */
    Page<ReimbursementItem> findByUserName(String userName, Pageable pageable);

    /**
     * 通过报销人查询统计
     *
     * @param userName
     * @return
     */
    int countByUserName(String userName);

    /**
     * 查找全部分页
     *
     * @param pageable
     * @return
     */
    Page<ReimbursementItem> findByUpdateTimeIsNotNullOrderByUpdateTimeDesc(Pageable pageable);

    /**
     * 通过支付月份和审批状态查询
     *
     * @param payment
     * @param approveStatus
     * @return
     */
    List<ReimbursementItem> findByPaymentMonthAndApproveStatus(String payment, String approveStatus);

    /**
     * 通过支付月份、审批状态、是否需要报销，进行查询。并且根据用户id排序
     *
     * @param payment
     * @param approveStatus
     * @return
     */
    List<ReimbursementItem> findByPaymentMonthAndApproveStatusAndNeedPayOrderByUserId(String payment, ReimbursementApproveStatusEnum approveStatus, ReimbursementNeedPayEnum needPay);
}

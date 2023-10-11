package com.hello.background.repository;

import com.hello.background.domain.KPI;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuketao
 * @Description
 */
@Repository
public interface KPIRepository extends PagingAndSortingRepository<KPI, Integer>, JpaSpecificationExecutor<KPI> {
    /**
     * 通过月份删除
     *
     * @param month
     */
    void deleteByMonth(String month);

    /**
     * 通过月份查询
     *
     * @param month
     * @return
     */
    List<KPI> findAllByMonth(String month);

    /**
     * 通过月份和用户名查询
     *
     * @param month
     * @param userName
     * @return
     */
    KPI findByMonthAndUserName(String month, String userName);
}

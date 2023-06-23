package com.hello.background.repository;

import com.hello.background.domain.KPIWorkDaysAdjust;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * KPI工作天数调整
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Repository
public interface KPIWorkDaysAdjustRepository extends JpaRepository<KPIWorkDaysAdjust, Integer>, JpaSpecificationExecutor<KPIWorkDaysAdjust> {
    List<KPIWorkDaysAdjust> findByAdjustDateBetween(Date start, Date end);

    List<KPIWorkDaysAdjust> findByUserNameAndAdjustDateBetween(String userName, Date start, Date end);
}

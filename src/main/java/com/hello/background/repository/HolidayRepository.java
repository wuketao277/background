package com.hello.background.repository;

import com.hello.background.constant.HolidayApproveStatusEnum;
import com.hello.background.domain.Holiday;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 假期
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Repository
public interface HolidayRepository extends PagingAndSortingRepository<Holiday, Integer>, JpaSpecificationExecutor<Holiday> {
    /**
     * 查询顾问在某段时间内的请假情况
     *
     * @param start
     * @param end
     * @param userName
     * @param approveStatus
     * @return
     */
    List<Holiday> findAllByHolidayDateBetweenAndUserNameAndApproveStatus(Date start, Date end, String userName, HolidayApproveStatusEnum approveStatus);
}

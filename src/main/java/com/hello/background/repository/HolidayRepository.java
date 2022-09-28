package com.hello.background.repository;

import com.hello.background.domain.Holiday;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 假期
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Repository
public interface HolidayRepository extends PagingAndSortingRepository<Holiday, Integer>, JpaSpecificationExecutor<Holiday> {
}

package com.hello.background.repository;

import com.hello.background.domain.MySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Repository
public interface MyScheduleRepository extends JpaRepository<MySchedule, Integer> {
}

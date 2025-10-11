package com.hello.background.repository;

import com.hello.background.domain.SchoolType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 学校仓库
 *
 * @author wuketao
 * @date 2025/10/10
 * @Description
 */
@Repository
public interface SchoolTypeRepository extends PagingAndSortingRepository<SchoolType, Integer>, JpaSpecificationExecutor<SchoolType> {
    SchoolType findBySchoolName(String schoolName);
}

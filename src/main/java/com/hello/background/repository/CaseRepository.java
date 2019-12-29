package com.hello.background.repository;

import com.hello.background.domain.Case;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Repository
public interface CaseRepository extends JpaRepository<Case, Integer> {

    Page<Case> findByTitleLike(String title, Pageable pageable);

    int countByTitleLike(String title);
}

package com.hello.background.repository;

import com.hello.background.domain.ClientCase;
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
public interface CaseRepository extends JpaRepository<ClientCase, Integer> {

    Page<ClientCase> findByTitleLikeOrDescriptionLike(String title, String description, Pageable pageable);

    int countByTitleLikeOrDescriptionLike(String title, String description);
}

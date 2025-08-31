package com.hello.background.repository;

import com.hello.background.constant.CaseStatusEnum;
import com.hello.background.domain.ClientCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Repository
public interface CaseRepository extends PagingAndSortingRepository<ClientCase, Integer>, JpaSpecificationExecutor<ClientCase> {

    Page<ClientCase> findByTitleLikeOrDescriptionLike(String title, String description, Pageable pageable);

    List<ClientCase> findByTitleLikeOrDescriptionLikeOrderByIdDesc(String title, String description);

    List<ClientCase> findByTitleLikeAndStatusOrderByIdDesc(String title, CaseStatusEnum status);

    List<ClientCase> findByTitleLikeOrderByIdDesc(String title);

    List<ClientCase> findByCwUserNameAndStatus(String cwUserName, CaseStatusEnum status);

    int countByTitleLikeOrDescriptionLike(String title, String description);
}

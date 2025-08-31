package com.hello.background.repository;

import com.hello.background.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 候选人仓库
 *
 * @author wuketao
 * @date 2019/12/29
 * @Description
 */
@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Integer>, JpaSpecificationExecutor<Client> {

    Page<Client> findByEnglishNameLikeOrChineseNameLike(String englishName, String chineseName, Pageable pageable);

    int countByEnglishNameLikeOrChineseNameLike(String englishName, String chineseName);

    Client queryById(Integer id);
}

package com.hello.background.repository;

import com.hello.background.domain.ClientLinkMan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 客户联系人仓库
 *
 * @author wuketao
 * @date 2019/12/29
 * @Description
 */
@Repository
public interface ClientLinkManRepository extends JpaRepository<ClientLinkMan, Integer> {

    Page<ClientLinkMan> findByEnglishNameLikeOrChineseNameLike(String englishName, String chineseName, Pageable pageable);

    int countByEnglishNameLikeOrChineseNameLike(String englishName, String chineseName);
}

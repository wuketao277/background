package com.hello.background.repository;

import com.hello.background.domain.BigEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 大事件参考
 */
@Repository
public interface BigEventRepository extends PagingAndSortingRepository<BigEvent, Integer>, JpaSpecificationExecutor<BigEvent> {
    /**
     * 根据标题或详细内容模糊查询
     *
     * @param title
     * @param detail
     * @param pageable
     * @return
     */
    Page<BigEvent> findByTitleLikeOrDetailLike(String title, String detail, Pageable pageable);

    /**
     * 根据标题或详细内容模糊查询
     *
     * @param title
     * @param detail
     * @return
     */
    int countByTitleLikeOrDetailLike(String title, String detail);
}

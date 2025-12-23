package com.hello.background.repository;

import com.hello.background.domain.Comment;
import com.hello.background.domain.PRCComment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuketao
 * @date 2025/12/23
 * @Description
 */
@Repository
public interface PRCCommentRepository extends JpaSpecificationExecutor<Comment>, PagingAndSortingRepository<PRCComment, Integer> {
    List<PRCComment> findAllByPrcIdOrderByIdDesc(Integer prcId);
}

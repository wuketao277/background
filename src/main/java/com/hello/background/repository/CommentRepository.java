package com.hello.background.repository;

import com.hello.background.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuketao
 * @date 2019/12/22
 * @Description
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByCandidateId(Integer candidateId);

    List<Comment> findByContentLikeOrderByCandidateIdAscIdAsc(String content);
}

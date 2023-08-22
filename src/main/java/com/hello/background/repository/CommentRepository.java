package com.hello.background.repository;

import com.hello.background.domain.Comment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wuketao
 * @date 2019/12/22
 * @Description
 */
@Repository
public interface CommentRepository extends JpaSpecificationExecutor<Comment>, PagingAndSortingRepository<Comment, Integer> {
    List<Comment> findAllByCandidateId(Integer candidateId);

    /**
     * 通过候选人id和顾问登录名来查询
     * @param candidateId
     * @param username
     * @return
     */
    List<Comment> findAllByCandidateIdAndUsername(Integer candidateId, String username);
    /**
     * 通过候选人ID和职位ID查询
     *
     * @param candidateId
     * @param caseId
     * @return
     */
    List<Comment> findAllByCandidateIdAndCaseId(Integer candidateId, Integer caseId);

    List<Comment> findAllByCandidateIdOrderByInputTimeDesc(Integer candidateId);

    List<Comment> findByContentLikeOrderByCandidateIdAscIdAsc(String content);

    List<Comment> findByInputTimeBetween(LocalDateTime begin, LocalDateTime end);

    List<Comment> findByInputTimeBetweenAndUsername(LocalDateTime begin, LocalDateTime end, String username);

    List<Comment> findByInputTimeBetweenAndUsernameIn(LocalDateTime begin, LocalDateTime end, List<String> usernameList);

    List<Comment> findByContentLikeOrderByIdDesc(String content);

    List<Comment> findByPhaseInAndInterviewTimeGreaterThanEqual(List<String> phaseList, LocalDateTime interviewTime);

    List<Comment> findByUsernameInAndPhaseInAndInterviewTimeGreaterThanEqual(List<String> usernameList, List<String> phaseList, LocalDateTime interviewTime);
}

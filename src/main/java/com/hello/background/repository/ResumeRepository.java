package com.hello.background.repository;

import com.hello.background.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {

    /**
     * 通过候选人id删除
     *
     * @param candidateId
     * @return
     */
    int deleteByCandidateId(Integer candidateId);

    /**
     * 通过candidateId查找
     *
     * @param candidateId
     * @return
     */
    List<Resume> findByCandidateIdOrderById(Integer candidateId);

    /**
     * 简历内容模糊匹配
     *
     * @param content
     * @return
     */
    List<Resume> findByContentLikeOrderByCandidateIdAscIdAsc(String content);
}

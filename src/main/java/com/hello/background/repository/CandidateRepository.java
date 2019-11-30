package com.hello.background.repository;

import com.hello.background.domain.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    /**
     * 通过中文名称查找候选人
     *
     * @param chineseName
     * @return
     */
    List<Candidate> findByChineseName(String chineseName);

}

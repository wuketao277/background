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
     * 通过英文名字查找
     *
     * @param englishName
     * @return
     */
    List<Candidate> findByEnglishName(String englishName);
}

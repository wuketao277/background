package com.hello.background.repository;

import com.hello.background.domain.CaseAttention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Repository
public interface CaseAttentionRepository extends JpaRepository<CaseAttention, Integer>, JpaSpecificationExecutor<CaseAttention> {

    List<CaseAttention> findByCaseIdAndUserName(Integer CaseId, String userName);

    List<CaseAttention> findByUserName(String userName);

    List<CaseAttention> findByUserNameOrderByClientChineseNameAscIdDesc(String userName);

    void deleteByCaseIdAndUserName(Integer CaseId, String userName);


}

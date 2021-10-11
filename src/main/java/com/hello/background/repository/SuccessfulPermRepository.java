package com.hello.background.repository;

import com.hello.background.domain.SuccessfulPerm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuketao
 * @date 2019/12/6
 * @Description
 */
@Repository
public interface SuccessfulPermRepository extends JpaRepository<SuccessfulPerm, Integer> {
    Page<SuccessfulPerm> findByClientNameLikeOrCandidateChineseNameLikeOrConsultantRealNameLikeOrConsultantUserNameLike(String clientName, String candidateChineseName, String consultantRealName, String consultantUserName, Pageable pageable);

    int countByClientNameLikeOrCandidateChineseNameLikeOrConsultantRealNameLikeOrConsultantUserNameLike(String clientName, String candidateChineseName, String consultantRealName, String consultantUserName);
}

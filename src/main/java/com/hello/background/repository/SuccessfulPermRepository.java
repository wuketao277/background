package com.hello.background.repository;

import com.hello.background.domain.SuccessfulPerm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author wuketao
 * @date 2019/12/6
 * @Description
 */
@Repository
public interface SuccessfulPermRepository extends JpaRepository<SuccessfulPerm, Integer> {
    Page<SuccessfulPerm> findByClientNameLikeOrCandidateChineseNameLikeOrConsultantRealNameLikeOrConsultantUserNameLikeOrConsultantRealName2LikeOrConsultantUserName2LikeOrConsultantRealName3LikeOrConsultantUserName3LikeOrConsultantRealName4LikeOrConsultantUserName4LikeOrConsultantRealName5LikeOrConsultantUserName5Like(String clientName, String candidateChineseName, String consultantRealName, String consultantUserName, String consultantRealName2, String consultantUserName2, String consultantRealName3, String consultantUserName3, String consultantRealName4, String consultantUserName4, String consultantRealName5, String consultantUserName5, Pageable pageable);

    int countByClientNameLikeOrCandidateChineseNameLikeOrConsultantRealNameLikeOrConsultantUserNameLikeOrConsultantRealName2LikeOrConsultantUserName2LikeOrConsultantRealName3LikeOrConsultantUserName3LikeOrConsultantRealName4LikeOrConsultantUserName4LikeOrConsultantRealName5LikeOrConsultantUserName5Like(String clientName, String candidateChineseName, String consultantRealName, String consultantUserName, String consultantRealName2, String consultantUserName2, String consultantRealName3, String consultantUserName3, String consultantRealName4, String consultantUserName4, String consultantRealName5, String consultantUserName5);

    /**
     * 通过审批状态和付款日期查询
     *
     * @return
     */
    List<SuccessfulPerm> findByApproveStatusAndActualAcceptDateBetween(String approveStatus, Date start, Date end);
}

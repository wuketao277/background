package com.hello.background.repository;

import com.hello.background.domain.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * 通过中文名称查找候选人
     *
     * @param chineseName
     * @return
     */
    List<Candidate> findByChineseName(String chineseName);

    /**
     * 获取符合条件的分页记录
     *
     * @param chineseName
     * @param englishName
     * @param pageable
     * @return
     */
    Page<Candidate> findByChineseNameLikeOrEnglishNameLikeOrCompanyNameLike(String chineseName, String englishName, String companyName, Pageable pageable);

    /**
     * 获取符合条件的分页记录
     *
     * @param chineseName
     * @param englishName
     * @param pageable
     * @return
     */
    Page<Candidate> findByChineseNameLikeOrEnglishNameLikeOrPhoneNoLikeOrEmailLikeOrCompanyNameLikeOrDepartmentLikeOrTitleLikeOrSchoolNameLikeOrCurrentAddressLikeOrFutureAddressLikeOrRemarkLikeOrderByIdDesc(String chineseName, String englishName, String phoneNo, String email, String companyName, String department, String title, String schoolName, String currentAddress, String futureAddress, String remark, Pageable pageable);

    /**
     * 通过条件模糊匹配
     *
     * @return
     */
    List<Candidate> findByChineseNameLikeOrEnglishNameLikeOrPhoneNoLikeOrEmailLikeOrCompanyNameLikeOrDepartmentLikeOrTitleLikeOrSchoolNameLikeOrCurrentAddressLikeOrFutureAddressLikeOrRemarkLikeOrderByIdDesc(
            String chineseName, String englishName, String phoneNo, String email, String companyName, String department, String title, String schoolName, String currentAddress, String futureAddress, String remark
    );

    /**
     * 计算符合条件的总记录数
     *
     * @param chineseName
     * @param englishName
     * @return
     */
    long countByChineseNameLikeOrEnglishNameLikeOrPhoneNoLikeOrEmailLikeOrCompanyNameLikeOrDepartmentLikeOrTitleLikeOrSchoolNameLikeOrCurrentAddressLikeOrFutureAddressLikeOrRemarkLike(
            String chineseName, String englishName, String phoneNo, String email, String companyName, String department, String title, String schoolName, String currentAddress, String futureAddress, String remark
    );

    /**
     * 通过手机号查询
     *
     * @param phoneNo
     * @return
     */
    List<Candidate> findByPhoneNo(String phoneNo);
}

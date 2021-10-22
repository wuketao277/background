package com.hello.background.repository;

import com.hello.background.domain.SalarySpecialItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuketao
 * @date 2019/12/6
 * @Description
 */
@Repository
public interface SalarySpecialItemRepository extends JpaRepository<SalarySpecialItem, Integer> {
    Page<SalarySpecialItem> findByConsultantRealNameLikeOrConsultantUserNameLikeOrMonthLikeOrDescriptionLikeOrderByUpdateTimeDesc(String consultantRealName, String consultantUserName, String month, String description, Pageable pageable);

    int countByConsultantRealNameLikeOrConsultantUserNameLikeOrMonthLikeOrDescriptionLike(String consultantRealName, String consultantUserName, String month, String description);

    /**
     * 通过月份查找特殊项
     *
     * @param month
     * @return
     */
    List<SalarySpecialItem> findByMonth(String month);

    /**
     * 通过顾问登录名查找特殊项
     *
     * @return
     */
    Page<SalarySpecialItem> findByConsultantUserNameOrderByMonthDesc(String consultantUserName, Pageable pageable);

    /**
     * 通过顾问登录名查找特殊项
     *
     * @param consultantUserName
     * @return
     */
    int countByConsultantUserName(String consultantUserName);

}

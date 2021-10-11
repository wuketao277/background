package com.hello.background.repository;

import com.hello.background.domain.SalarySpecialItem;
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
public interface SalarySpecialItemRepository extends JpaRepository<SalarySpecialItem, Integer> {
    Page<SalarySpecialItem> findByConsultantRealNameLikeOrConsultantUserNameLikeOrMonthLikeOrDescriptionLikeOrderByUpdateTimeDesc(String consultantRealName, String consultantUserName, String month, String description, Pageable pageable);

    int countByConsultantRealNameLikeOrConsultantUserNameLikeOrMonthLikeOrDescriptionLikeOrderByUpdateTimeDesc(String consultantRealName, String consultantUserName, String month, String description);
}

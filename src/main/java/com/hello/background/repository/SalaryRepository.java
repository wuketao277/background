package com.hello.background.repository;

import com.hello.background.domain.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 工资仓库
 *
 * @author wuketao
 * @date 2019/12/6
 * @Description
 */
@Repository
public interface SalaryRepository extends PagingAndSortingRepository<Salary, Integer>, JpaSpecificationExecutor<Salary> {

    /**
     * 分页查询
     *
     * @param consultantRealName
     * @param consultantUserName
     * @param month
     * @param pageable
     * @return
     */
    Page<Salary> findByConsultantRealNameLikeOrConsultantUserNameLikeOrMonthLikeOrderByMonthDescSumDescHistoryDebtAsc(String consultantRealName, String consultantUserName, String month, Pageable pageable);

    /**
     * 查询总数
     *
     * @param consultantRealName
     * @param consultantUserName
     * @param month
     * @return
     */
    int countByConsultantRealNameLikeOrConsultantUserNameLikeOrMonthLike(String consultantRealName, String consultantUserName, String month);

    /**
     * 分页查询
     *
     * @param consultantUserName
     * @param pageable
     * @return
     */
    Page<Salary> findByConsultantUserNameOrderByMonthDesc(String consultantUserName, Pageable pageable);

    /**
     * 查询总数
     *
     * @param consultantUserName
     * @return
     */
    int countByConsultantUserName(String consultantUserName);


    /**
     * 通过月份删除
     *
     * @param month
     * @return
     */
    int deleteByMonth(String month);

    /**
     * 通过顾问姓名查找工资记录，倒排序
     *
     * @param consultantUserName
     * @return
     */
    List<Salary> findByConsultantUserNameOrderByMonthDesc(String consultantUserName);

    /**
     * 通过月份获取薪资信息
     *
     * @param month
     * @return
     */
    List<Salary> findAllByMonth(String month);

    /**
     * 通过顾问登录名和月份查询
     *
     * @param userName
     * @param month
     * @return
     */
    Salary findByConsultantUserNameAndMonth(String userName, String month);
}

package com.hello.background.repository;

import com.hello.background.domain.MyTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Repository
public interface MyTaskRepository extends PagingAndSortingRepository<MyTask, Integer>, JpaSpecificationExecutor<MyTask> {

    /**
     * 通过后续人id查询
     *
     * @param relativeCandidateId
     * @return
     */
    List<MyTask> findByRelativeCandidateId(Integer relativeCandidateId);

    /**
     * 通过任务执行人与执行日志查询
     *
     * @param executeUserName 执行人登录名
     * @param executeDate     执行日期
     * @return 任务集合
     */
    List<MyTask> findByExecuteUserNameAndFinishedAndExecuteDateLessThanEqual(String executeUserName, Boolean finished, LocalDate executeDate);

    /**
     * 通过 任务名称 执行人 创建人 模糊匹配
     *
     * @param taskTitle       任务名称
     * @param executeRealName 创建人
     * @param createRealName  执行人
     * @return
     */
    Page<MyTask> findByTaskTitleLikeOrExecuteRealNameLikeOrCreateRealNameLike(String taskTitle, String executeRealName, String createRealName, Pageable pageable);

    /**
     * 通过 任务名称 执行人 创建人 模糊匹配
     *
     * @param taskTitle       任务名称
     * @param executeRealName 创建人
     * @param createRealName  执行人
     * @return
     */
    long countByTaskTitleLikeOrExecuteRealNameLikeOrCreateRealNameLike(String taskTitle, String executeRealName, String createRealName);
}

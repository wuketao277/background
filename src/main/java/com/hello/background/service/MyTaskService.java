package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.domain.MyTask;
import com.hello.background.repository.MyTaskRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.MyTaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuketao
 * @date 2020/2/2
 * @Description
 */
@Transactional
@Service
public class MyTaskService {
    @Autowired
    private MyTaskRepository myTaskRepository;

    /**
     * 保存任务
     *
     * @param vo
     * @return
     */
    public MyTaskVO save(MyTaskVO vo) {
        MyTask myTask = myTaskRepository.save(TransferUtil.transferTo(vo, MyTask.class));
        return TransferUtil.transferTo(myTask, MyTaskVO.class);
    }

    /**
     * 通过后续人id查询
     *
     * @param relativeCandidateId
     * @return
     */
    public List<MyTaskVO> findByRelativeCandidateId(Integer relativeCandidateId) {
        List<MyTask> taskList = myTaskRepository.findByRelativeCandidateId(relativeCandidateId);
        return taskList.stream().map(task -> TransferUtil.transferTo(task, MyTaskVO.class)).collect(Collectors.toList());
    }

    /**
     * 通过任务执行人与执行日志查询
     *
     * @param executeUserName 执行人登录名
     * @param executeDate     执行日期
     * @return 任务集合
     */
    public List<MyTaskVO> findByExecuteUserNameAndExecuteDate(String executeUserName, LocalDate executeDate) {
        List<MyTask> myTaskList = myTaskRepository.findByExecuteUserNameAndExecuteDate(executeUserName, executeDate);
        return myTaskList.stream().map(task -> TransferUtil.transferTo(task, MyTaskVO.class)).collect(Collectors.toList());
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<MyTaskVO> queryMyTaskPage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<MyTask> myTaskPage = null;
        long total = 0;
        if (Strings.isNullOrEmpty(search)) {
            myTaskPage = myTaskRepository.findAll(pageable);
            total = myTaskRepository.count();
        } else {
            myTaskPage = myTaskRepository.findByTaskTitleLikeOrExecuteRealNameLikeOrCreateRealNameLike(search, search, search, pageable);
            total = myTaskRepository.countByTaskTitleLikeOrExecuteRealNameLikeOrCreateRealNameLike(search, search, search);
        }
        Page<MyTaskVO> map = myTaskPage.map(x -> TransferUtil.transferTo(x, MyTaskVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
        return map;
    }
}

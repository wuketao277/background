package com.hello.background.service;

import com.hello.background.domain.MyTask;
import com.hello.background.repository.MyTaskRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.MyTaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuketao
 * @date 2020/2/2
 * @Description
 */
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
}

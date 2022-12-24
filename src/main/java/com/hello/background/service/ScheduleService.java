package com.hello.background.service;

import com.hello.background.constant.JobTypeEnum;
import com.hello.background.domain.User;
import com.hello.background.repository.SuccessfulPermRepository;
import com.hello.background.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时任务服务
 *
 * @author wuketao
 * @date 2022/12/24
 * @Description
 */
@Slf4j
@Component
public class ScheduleService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SuccessfulPermRepository successfulPermRepository;

    /**
     * 外包员工成功case检查
     */
//    @Scheduled(cron = "0 0/1 * * * *")
    public void contractingSuccessfulCaseCheck() {
        // 获取所有在职外包员工信息
        List<User> allUserList = userRepository.findAll();
        // 筛选在职的外包员工
        List<User> contractingList = allUserList.stream()
                .filter(u -> u.getJobType() == JobTypeEnum.CONSULTANT)
                .filter(u -> null != u.getOnBoardDate() // 存在入职日期
                        && u.getOnBoardDate().compareTo(new Date()) <= 0 // 入职日期小于等于当前日期
                        && (null == u.getDimissionDate() || u.getDimissionDate().compareTo(new Date()) >= 0) // 没有离职日期或者离职日期大于等于当前日期
                )
                .collect(Collectors.toList());
        // 遍历每一个外包员工，判断是否有当前月的成功case
        for (User contracting : contractingList) {

        }
    }
}

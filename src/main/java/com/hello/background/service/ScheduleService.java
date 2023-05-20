package com.hello.background.service;

import com.hello.background.constant.JobTypeEnum;
import com.hello.background.domain.Candidate;
import com.hello.background.domain.Comment;
import com.hello.background.domain.User;
import com.hello.background.repository.CandidateRepository;
import com.hello.background.repository.CommentRepository;
import com.hello.background.repository.SuccessfulPermRepository;
import com.hello.background.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CommentRepository commentRepository;

    /**
     * 候选人生日提醒
     */
    @Scheduled(cron = "0 0/1 * * * *")
    public void generateCVORecommendationRemind() {

    }

    /**
     * 候选人生日提醒
     */
//    @Scheduled(cron = "0 0/1 * * * *")
    public void candidateBirthdayRemind() {
        // 每次从0页开始分页查询
        Integer pageIndex = 0;
        // 每页1000条
        Integer pageSize = 1000;
        // 今天
        LocalDate toDay = LocalDate.now();
        // 明天
        LocalDate tomorrow = toDay.plusDays(1);
        // 后天
        LocalDate afterTomorrow = toDay.plusDays(2);
        // 获取所有用户
        List<User> userList = userRepository.findAll();
        // 通过循环查询
        while (true) {
            Pageable pageable = new PageRequest(pageIndex, pageSize, Sort.Direction.ASC, "id");
            // 获取所有生日字段不为空的候选人
            List<Candidate> list = candidateRepository.findAllByBirthDayIsNotNull(pageable);
            list.forEach(candidate -> {
                try {
                    // 获取生日日期格式
                    LocalDate brithday = LocalDate.parse(candidate.getBirthDay());
                    // 如果生日和今天 或明天 或后天 的月、日相同，就发送祝福。
                    if ((brithday.getDayOfMonth() == toDay.getDayOfMonth() && brithday.getMonthValue() == toDay.getMonthValue())
                            || (brithday.getDayOfMonth() == tomorrow.getDayOfMonth() && brithday.getMonthValue() == tomorrow.getMonthValue())
                            || (brithday.getDayOfMonth() == afterTomorrow.getDayOfMonth() && brithday.getMonthValue() == afterTomorrow.getMonthValue())
                    ) {
                        // 首先获取候选人的评论
                        List<Comment> commentList = commentRepository.findAllByCandidateId(candidate.getId());
                        // 获取给候选人写过评论的用户
                        Set<String> userNameSet = commentList.stream().map(c -> c.getUsername()).filter(c -> c.indexOf("HelloDay") == -1).collect(Collectors.toSet());
                        // 遍历用户，创建生日祝福任务
                        for (String userName : userNameSet) {
                            // 获取正常状态的用户
                            Optional<User> userOptional = userList.stream().filter(u -> u.getUsername().equals(userName) && u.getEnabled()).findFirst();
                            if (userOptional.isPresent()) {
                                User user = userOptional.get();

                            }
                        }
                    }
                } catch (Exception ex) {
                    // 异常不用做任何处理
                }
            });
            // 如果查询到的结果小于分页数据，就结束循环。
            if (list.size() < pageSize) {
                break;
            }
        }
    }

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

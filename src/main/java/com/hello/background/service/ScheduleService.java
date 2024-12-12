package com.hello.background.service;

import com.hello.background.constant.JobTypeEnum;
import com.hello.background.domain.*;
import com.hello.background.repository.*;
import com.hello.background.utils.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Autowired
    private MyTaskRepository myTaskRepository;

    /**
     * 凌晨1点生成follow候选人的定时任务
     */
//    @Scheduled(cron = "0 0 1 * * *")
    public void generateTaskForFollowCandidate() {
        if (LocalDate.now().getDayOfWeek().getValue() != 1) {
            // 只有周一才创建任务
            return;
        }
        Date today000 = DateTimeUtil.getToday000();
        // 查询所有成功case
        Iterable<SuccessfulPerm> successfulPermIterable = successfulPermRepository.findAll();
        for (SuccessfulPerm successfulPerm : successfulPermIterable) {
            if (Strings.isBlank(successfulPerm.getType()) && !"perm".equals(successfulPerm.getType())) {
                // 非perm类型的成功case，直接返回
                continue;
            }
            // 如果还未入职，创建follow候选人入职任务
            Date onBoardDate = successfulPerm.getOnBoardDate();
            if (null != onBoardDate && onBoardDate.compareTo(today000) > 0) {
                // 给leader创建任务
                createTask(successfulPerm.getLeaderUserName(), successfulPerm, "follow入职");
                // 给顾问1创建任务
                createTask(successfulPerm.getConsultantUserName(), successfulPerm, "follow入职");
                // 给顾问2创建任务
                createTask(successfulPerm.getConsultantUserName2(), successfulPerm, "follow入职");
                // 给顾问3创建任务
                createTask(successfulPerm.getConsultantUserName3(), successfulPerm, "follow入职");
                // 给顾问4创建任务
                createTask(successfulPerm.getConsultantUserName4(), successfulPerm, "follow入职");
            } else {
                // 如果已入职但还在保证期中的，创建follow候选人近况任务
                Date guaranteeDate = successfulPerm.getGuaranteeDate();
                if (null != guaranteeDate && guaranteeDate.compareTo(today000) > 0) {
                    if (null != onBoardDate && (today000.getTime() - onBoardDate.getTime()) < 30 * 24 * 60 * 60) {
                        // 入职后一个月以内，每周都要follow候选人
                        // 给leader创建任务
                        createTask(successfulPerm.getLeaderUserName(), successfulPerm, "follow工作情况");
                        // 给顾问1创建任务
                        createTask(successfulPerm.getConsultantUserName(), successfulPerm, "follow工作情况");
                        // 给顾问2创建任务
                        createTask(successfulPerm.getConsultantUserName2(), successfulPerm, "follow工作情况");
                        // 给顾问3创建任务
                        createTask(successfulPerm.getConsultantUserName3(), successfulPerm, "follow工作情况");
                        // 给顾问4创建任务
                        createTask(successfulPerm.getConsultantUserName4(), successfulPerm, "follow工作情况");
                    } else if (LocalDate.now().getDayOfMonth() < 7) {
                        // 入职后一个月以外，每月都要follow候选人
                        // 给leader创建任务
                        createTask(successfulPerm.getLeaderUserName(), successfulPerm, "follow工作情况");
                        // 给顾问1创建任务
                        createTask(successfulPerm.getConsultantUserName(), successfulPerm, "follow工作情况");
                        // 给顾问2创建任务
                        createTask(successfulPerm.getConsultantUserName2(), successfulPerm, "follow工作情况");
                        // 给顾问3创建任务
                        createTask(successfulPerm.getConsultantUserName3(), successfulPerm, "follow工作情况");
                        // 给顾问4创建任务
                        createTask(successfulPerm.getConsultantUserName4(), successfulPerm, "follow工作情况");
                    }
                }
            }
        }
    }

    /**
     * 通过成功case，创建任务
     *
     * @param userName
     * @param successfulPerm
     */
    private void createTask(String userName, SuccessfulPerm successfulPerm, String title) {
        if (Strings.isNotBlank(userName)) {
            User user = userRepository.findByUsername(userName);
            if (null != user.getDimissionDate()) {
                // 离职的不用创建任务，直接返回
                return;
            }
            MyTask task = new MyTask();
            task.setRelativeCandidateId(successfulPerm.getCandidateId());
            task.setRelativeCandidateChineseName(successfulPerm.getCandidateChineseName());
            task.setTaskTitle(title);
            task.setTaskContent(title);
            task.setExecuteDate(LocalDate.now());
            task.setExecuteUserName(user.getUsername());
            task.setExecuteRealName(user.getRealname());
            task.setCreateRealName("吴克涛");
            task.setCreateUserName("Howard");
            task.setCreateDateTime(LocalDateTime.now());
            myTaskRepository.save(task);
        }
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

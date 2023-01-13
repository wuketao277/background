package com.hello.background.service;

import com.hello.background.constant.RoleEnum;
import com.hello.background.domain.MyTask;
import com.hello.background.repository.MyTaskRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
     * 更新任务
     *
     * @param vo
     * @return
     */
    public boolean update(MyTaskUpdateVO vo) {
        Optional<MyTask> optionalMyTask = myTaskRepository.findById(vo.getId());
        if (optionalMyTask.isPresent()) {
            MyTask myTask = optionalMyTask.get();
            myTask.setFinished(vo.getFinished());
            myTask.setExecuteResult(vo.getExecuteResult());
            myTaskRepository.save(myTask);
            return true;
        } else {
            return false;
        }
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
    public List<MyTaskVO> findByExecuteUserNameAndFinishedAndExecuteDateLessThanEqual(String executeUserName, Boolean finished, LocalDate executeDate) {
        List<MyTask> myTaskList = myTaskRepository.findByExecuteUserNameAndFinishedAndExecuteDateLessThanEqual(executeUserName, finished, executeDate);
        return myTaskList.stream().map(task -> TransferUtil.transferTo(task, MyTaskVO.class)).collect(Collectors.toList());
    }

    /**
     * 查询分页
     *
     * @return
     */
    public Page<MyTaskVO> queryMyTaskPage(MyTaskPageQueryVO queryVO, HttpSession session) {
        // 获取用户
        UserVO user = (UserVO) session.getAttribute("user");
        List<Sort.Order> orderList = new ArrayList<>();
        orderList.add(new Sort.Order(Sort.Direction.ASC, "finished"));
        orderList.add(new Sort.Order(Sort.Direction.ASC, "executeDate"));
        Pageable pageable = new PageRequest(queryVO.getCurrentPage() - 1, queryVO.getPageSize(), new Sort(orderList));
        Specification<MyTask> specification = new Specification<MyTask>() {
            @Override
            public Predicate toPredicate(Root<MyTask> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                MyTaskPageQuerySearchVO search = queryVO.getSearch();
                if (null != search) {
                    if (!StringUtils.isEmpty(search.getExecuteUserName())) {
                        Path<String> path = root.get("executeUserName");
                        Predicate equal = criteriaBuilder.equal(path, search.getExecuteUserName());
                        list.add(criteriaBuilder.and(equal));
                    }
                    if ("FINISHED".equals(search.getFinished())) {
                        Path<Boolean> path = root.get("finished");
                        Predicate equal = criteriaBuilder.equal(path, Boolean.TRUE);
                        list.add(criteriaBuilder.and(equal));
                    } else if ("UNFINISHED".equals(search.getFinished())) {
                        Path<Boolean> path = root.get("finished");
                        Predicate equal = criteriaBuilder.equal(path, Boolean.FALSE);
                        list.add(criteriaBuilder.and(equal));
                    }
                    if (!StringUtils.isEmpty(search.getTaskTitle())) {
                        Path<String> path = root.get("taskTitle");
                        Predicate like = criteriaBuilder.like(path, search.getTaskTitle());
                        list.add(criteriaBuilder.and(like));
                    }
                    if (!StringUtils.isEmpty(search.getTaskContent())) {
                        Path<String> path = root.get("taskContent");
                        Predicate like = criteriaBuilder.like(path, search.getTaskContent());
                        list.add(criteriaBuilder.and(like));
                    }
                    // 非管理员只能查询自己的任务或者是自己创建的任务
                    if (!user.getRoles().contains(RoleEnum.ADMIN) && !user.getRoles().contains(RoleEnum.ADMIN_COMPANY)) {
                        Path<String> path = root.get("executeUserName");
                        Predicate equal = criteriaBuilder.equal(path, user.getUsername());
                        Path<String> path2 = root.get("createUserName");
                        Predicate equal2 = criteriaBuilder.equal(path2, user.getUsername());
                        list.add(criteriaBuilder.or(equal, equal2));
                    }
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        Page<MyTask> myTaskPage = myTaskRepository.findAll(specification, pageable);
        Page<MyTaskVO> map = myTaskPage.map(x -> TransferUtil.transferTo(x, MyTaskVO.class));
        return map;
    }
}

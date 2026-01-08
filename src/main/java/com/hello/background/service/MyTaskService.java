package com.hello.background.service;

import com.hello.background.constant.RoleEnum;
import com.hello.background.domain.MyTask;
import com.hello.background.repository.MyTaskRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.MyTaskPageQueryVO;
import com.hello.background.vo.MyTaskUpdateVO;
import com.hello.background.vo.MyTaskVO;
import com.hello.background.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            myTask.setExecuteDate(vo.getExecuteDate());
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
     * 通过PRCid查询
     *
     * @param relativePRCId
     * @return
     */
    public List<MyTaskVO> findByRelativePRCId(Integer relativePRCId) {
        List<MyTask> taskList = myTaskRepository.findByRelativePRCId(relativePRCId);
        return taskList.stream().map(task -> TransferUtil.transferTo(task, MyTaskVO.class)).collect(Collectors.toList());
    }

    /**
     * 通过任务执行人与执行日志查询
     *
     * @param executeUserName 执行人登录名
     * @param executeDate     执行日期
     * @return 任务集合
     */
    public List<MyTaskVO> findNotFinishTask(String executeUserName, LocalDate executeDate) {
        List<MyTask> myTaskList = myTaskRepository.findByExecuteUserNameAndExecuteDateLessThanEqual(executeUserName, executeDate);
        // 只保留未完成的任务
        myTaskList = myTaskList.stream().filter(t -> null == t.getFinished() || !t.getFinished()).collect(Collectors.toList());
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
        orderList.add(new Sort.Order(Sort.Direction.DESC, "executeDate"));
        Pageable pageable = new PageRequest(queryVO.getCurrentPage() - 1, queryVO.getPageSize(), new Sort(orderList));
        Specification<MyTask> specification = new Specification<MyTask>() {
            @Override
            public Predicate toPredicate(Root<MyTask> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                String search = queryVO.getSearch();
                if (null != search) {
                    Path<String> path1 = root.get("executeUserName");
                    Predicate equal1 = criteriaBuilder.equal(path1, search);
                    Path<String> path2 = root.get("relativeCandidateChineseName");
                    Predicate like2 = criteriaBuilder.like(path2, "%" + search + "%");
                    Path<String> path3 = root.get("taskTitle");
                    Predicate like3 = criteriaBuilder.like(path3, "%" + search + "%");
                    Path<String> path4 = root.get("taskContent");
                    Predicate like4 = criteriaBuilder.like(path4, "%" + search + "%");
                    list.add(criteriaBuilder.or(equal1, like2, like3, like4));
                }// 非管理员只能查询自己的任务或者是自己创建的任务
                if (!user.getRoles().contains(RoleEnum.ADMIN) && !user.getRoles().contains(RoleEnum.ADMIN_COMPANY)) {
                    Path<String> path = root.get("executeUserName");
                    Predicate equal = criteriaBuilder.equal(path, user.getUsername());
                    Path<String> path2 = root.get("createUserName");
                    Predicate equal2 = criteriaBuilder.equal(path2, user.getUsername());
                    list.add(criteriaBuilder.or(equal, equal2));
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

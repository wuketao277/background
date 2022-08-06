package com.hello.background.service;

import com.hello.background.domain.LessonQuestion;
import com.hello.background.repository.LessonQuestionRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.LessonQuestionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuketao
 * @date 2022/8/3
 * @Description
 */
@Transactional
@Slf4j
@Service
public class LessonQuestionService {

    @Autowired
    private LessonQuestionRepository lessonQuestionRepository;

    /**
     * 保持课程问题
     *
     * @param vo
     * @return
     */
    public LessonQuestionVO save(LessonQuestionVO vo) {
        LessonQuestion lessonQuestion = TransferUtil.transferTo(vo, LessonQuestion.class);
        lessonQuestion = lessonQuestionRepository.save(lessonQuestion);
        return TransferUtil.transferTo(lessonQuestion, LessonQuestionVO.class);
    }

    /**
     * 查询分页
     *
     * @param lessonId    课程id
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<LessonQuestionVO> queryPage(Integer lessonId, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<LessonQuestion> page = null;
        long total = 0;
        page = lessonQuestionRepository.findByLessonId(lessonId, pageable);
        total = lessonQuestionRepository.countByLessonId(lessonId);
        Page<LessonQuestionVO> map = page.map(x -> TransferUtil.transferTo(x, LessonQuestionVO.class));
        map = new PageImpl<>(map.getContent(),
                new PageRequest(map.getPageable().getPageNumber(), map.getPageable().getPageSize()),
                total);
        return map;
    }

    /**
     * 通过id查询课程
     *
     * @param id
     * @return
     */
    public LessonQuestionVO queryById(Integer id) {
        return TransferUtil.transferTo(lessonQuestionRepository.queryById(id), LessonQuestionVO.class);
    }

    /**
     * 通过id删除
     *
     * @param id
     */
    public void deleteById(Integer id) {
        lessonQuestionRepository.deleteById(id);
    }

    /**
     * 通过课程id查询所有问题
     *
     * @param lessonId
     * @return
     */
    public List<LessonQuestionVO> findByLessonId(Integer lessonId) {
        List<LessonQuestion> lessonQuestionList = lessonQuestionRepository.findByLessonId(lessonId);
        return lessonQuestionList.stream().map(x -> TransferUtil.transferTo(x, LessonQuestionVO.class)).collect(Collectors.toList());
    }
}

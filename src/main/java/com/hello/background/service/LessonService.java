package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.domain.Lesson;
import com.hello.background.repository.LessonRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.LessonVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wuketao
 * @date 2022/8/3
 * @Description
 */
@Transactional
@Slf4j
@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    /**
     * 保持课程
     *
     * @param vo
     * @return
     */
    public LessonVO save(LessonVO vo) {
        Lesson lesson = TransferUtil.transferTo(vo, Lesson.class);
        lesson = lessonRepository.save(lesson);
        return TransferUtil.transferTo(lesson, LessonVO.class);
    }

    /**
     * 查询分页
     *
     * @param search      搜索关键字
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<LessonVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<Lesson> page = null;
        long total = 0;
        if (Strings.isNullOrEmpty(search)) {
            page = lessonRepository.findAll(pageable);
            total = lessonRepository.count();
        } else {
            page = lessonRepository.findByLessonNameLike(search, pageable);
            total = lessonRepository.countByLessonNameLike(search);
        }
        Page<LessonVO> map = page.map(x -> TransferUtil.transferTo(x, LessonVO.class));
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
    public LessonVO queryById(Integer id) {
        return TransferUtil.transferTo(lessonRepository.queryById(id), LessonVO.class);
    }
}

package com.hello.background.service;

import com.google.common.base.Strings;
import com.hello.background.domain.LessonStudyRecord;
import com.hello.background.repository.LessonStudyRecordRepository;
import com.hello.background.utils.TransferUtil;
import com.hello.background.vo.LessonStudyRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author wuketao
 * @date 2022/8/3
 * @Description
 */
@Transactional
@Slf4j
@Service
public class LessonStudyRecordService {

    @Autowired
    private LessonStudyRecordRepository lessonStudyRecordRepository;

    /**
     * 保持课程学习记录
     *
     * @param vo
     * @return
     */
    public LessonStudyRecordVO save(LessonStudyRecordVO vo) {
        LessonStudyRecord lessonStudyRecord = TransferUtil.transferTo(vo, LessonStudyRecord.class);
        lessonStudyRecord = lessonStudyRecordRepository.save(lessonStudyRecord);
        return TransferUtil.transferTo(lessonStudyRecord, LessonStudyRecordVO.class);
    }

    /**
     * 查询分页
     *
     * @param currentPage 当前页
     * @param pageSize    页尺寸
     * @return
     */
    public Page<LessonStudyRecordVO> queryPage(String search, Integer currentPage, Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        Page<LessonStudyRecord> page = null;
        long total = 0;
        if (Strings.isNullOrEmpty(search)) {
            page = lessonStudyRecordRepository.findAll(pageable);
            total = lessonStudyRecordRepository.findAll().size();
        } else {
            search = "%" + search + "%";
            page = lessonStudyRecordRepository.findByLessonNameLikeOrUsernameLike(search, search, pageable);
            total = lessonStudyRecordRepository.countByLessonNameLikeOrUsernameLike(search, search);
        }
        Page<LessonStudyRecordVO> map = page.map(x -> TransferUtil.transferTo(x, LessonStudyRecordVO.class));
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
    public LessonStudyRecordVO queryById(Integer id) {
        return TransferUtil.transferTo(lessonStudyRecordRepository.findById(id).get(), LessonStudyRecordVO.class);
    }

    /**
     * 通过课程id和用户名查询课程学习记录
     *
     * @param lessonId
     * @param userName
     * @return
     */
    public LessonStudyRecordVO findByLessonIdAndUsername(Integer lessonId, String userName) {
        LessonStudyRecord lessonStudyRecord = lessonStudyRecordRepository.findByLessonIdAndUsername(lessonId, userName);

        return null == lessonStudyRecord ? null : TransferUtil.transferTo(lessonStudyRecord, LessonStudyRecordVO.class);
    }

    /**
     * 保存学习记录得分
     *
     * @param id
     * @param score
     */
    public void saveStudyRecordScore(Integer id, Integer score) {
        Optional<LessonStudyRecord> recordOptional = lessonStudyRecordRepository.findById(id);
        if (recordOptional.isPresent()) {
            LessonStudyRecord studyRecord = recordOptional.get();
            studyRecord.setScore(score);
            lessonStudyRecordRepository.save(studyRecord);
        }
    }
}

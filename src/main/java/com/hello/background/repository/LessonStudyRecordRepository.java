package com.hello.background.repository;

import com.hello.background.domain.LessonStudyRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuketao
 * @date 2022/8/3
 * @Description
 */
@Repository
public interface LessonStudyRecordRepository extends JpaRepository<LessonStudyRecord, Integer> {

    Page<LessonStudyRecord> findByLessonNameLikeOrUsernameLike(String lessonName, String userName, Pageable pageable);

    int countByLessonNameLikeOrUsernameLike(String lessonName, String userName);

    /**
     * 通过课程id和登录名查询
     *
     * @param lessonId
     * @param userName
     * @return
     */
    LessonStudyRecord findByLessonIdAndUsername(Integer lessonId, String userName);
}

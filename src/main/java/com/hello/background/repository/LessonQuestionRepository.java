package com.hello.background.repository;

import com.hello.background.domain.LessonQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuketao
 * @date 2022/8/3
 * @Description
 */
@Repository
public interface LessonQuestionRepository extends JpaRepository<LessonQuestion, Integer> {

    Page<LessonQuestion> findByLessonNameLikeOrLessonQuestionContentLike(String lessonName, String lessonQuestionContent, Pageable pageable);

    int countByLessonNameLikeOrLessonQuestionContentLike(String lessonName, String lessonQuestionContent);

    Page<LessonQuestion> findByLessonId(Integer lessonId, Pageable pageable);

    int countByLessonId(Integer lessonId);

    LessonQuestion queryById(Integer id);

    List<LessonQuestion> findByLessonId(Integer lessonId);

}

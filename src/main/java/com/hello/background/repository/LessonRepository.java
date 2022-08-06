package com.hello.background.repository;

import com.hello.background.domain.Lesson;
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
public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    Page<Lesson> findByLessonNameLike(String lessonName, Pageable pageable);

    int countByLessonNameLike(String lessonName);

    Lesson queryById(Integer id);
}

package com.hello.background.repository;

import com.hello.background.domain.MyNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 新闻领域对象 仓库
 *
 * @author wuketao
 * @date 2019/11/24
 * @Description
 */
@Repository
public interface MyNewsRepository extends JpaRepository<MyNews, Integer> {

    /**
     * 通过新闻名称或新闻内容，模糊匹配。获取分页数据
     *
     * @param title    新闻标题关键字
     * @param content  新闻内容关键字
     * @param pageable 分页信息
     * @return
     */
    Page<MyNews> findByTitleLikeOrContentLike(String title, String content, Pageable pageable);

    /**
     * 计算符合条件的记录数
     *
     * @param title   新闻标题关键字
     * @param content 新闻内容关键字
     * @return
     */
    long countByTitleLikeOrContentLike(String title, String content);
}

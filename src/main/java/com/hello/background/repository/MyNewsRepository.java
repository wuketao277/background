package com.hello.background.repository;

import com.hello.background.domain.MyNews;
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
}

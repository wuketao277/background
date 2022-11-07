package com.hello.background.repository;

import com.hello.background.domain.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 配置 仓库接口
 *
 * @author wuketao
 * @date 2022/11/04
 * @Description
 */
@Repository
public interface ConfigRepository extends JpaRepository<Config, Integer> {
    /**
     * 通过类别查询
     *
     * @param category
     * @return
     */
    List<Config> findAllByCategoryOrderById(String category);

    /**
     * 通过类别和类型查询
     *
     * @param category
     * @return
     */
    List<Config> findAllByCategoryAndTypeOrderById(String category, String type);

    /**
     * 通过类别、类型、编码查询
     *
     * @param category
     * @return
     */
    Config findFirstByCategoryAndTypeAndCodeOrderById(String category, String type, String code);
}

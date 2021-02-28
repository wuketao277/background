package com.hello.background.repository;

import com.hello.background.domain.UploadFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 上传文件 仓库
 *
 * @author wuketao
 * @date 2020/2/5
 * @Description
 */
public interface UploadFileRepository extends JpaRepository<UploadFile, Integer> {

    /**
     * 通过uuid查找上传文件记录
     *
     * @param uuid 文件唯一编号
     * @return 上传文件记录
     */
    UploadFile findByUuid(String uuid);

    /**
     * 通过关联表id和关联表名称获取上传文件记录集合
     *
     * @param relativeTableId   关联表id
     * @param relativeTableName 关联表名称
     * @return 上传文件记录集合
     */
    List<UploadFile> findByRelativeTableIdAndRelativeTableName(Integer relativeTableId, String relativeTableName);

    /**
     * 通过uuid删除
     *
     * @param uuid
     */
    void deleteByUuid(String uuid);

    /**
     * 获取符合条件的分页记录
     *
     * @param originalFileName
     * @param pageable
     * @return
     */
    Page<UploadFile> findByOriginalFileNameLike(String originalFileName, Pageable pageable);

    /**
     * 获取符合条件的数量
     *
     * @param originalFileName
     * @return
     */
    long countByOriginalFileNameLike(String originalFileName);

}

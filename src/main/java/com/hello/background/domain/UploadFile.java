package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 上传文件
 *
 * @author wuketao
 * @date 2019/12/28
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UploadFile {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 关联表名称
     */
    @Column(length = 100)
    private String relativeTableName;

    /**
     * 关联表id
     */
    @Column()
    private Integer relativeTableId;

    /**
     * uuid
     */
    @Column(length = 36, nullable = false)
    private String uuid;

    /**
     * 原始文件名
     */
    @Column(length = 200, nullable = false)
    private String originalFileName;

    /**
     * 创建时间
     */
    @Column
    private LocalDateTime createTime;

    /**
     * 创建人登录名
     */
    @Column(length = 50)
    private String createUserName;

    /**
     * 创建人名称
     */
    @Column(length = 50)
    private String createRealName;
}

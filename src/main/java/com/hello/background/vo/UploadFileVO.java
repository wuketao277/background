package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
public class UploadFileVO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 关联表名称
     */
    private String relativeTableName;

    /**
     * 关联表id
     */
    private Integer relativeTableId;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 原始文件名
     */
    private String originalFileName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人登录名
     */
    private String createUserName;

    /**
     * 创建人名称
     */
    private String createRealName;
}

package com.hello.background.vo;

import lombok.Data;

/**
 * 配置
 *
 * @author wuketao
 * @date 2022/11/04
 * @Description
 */
@Data
public class ConfigVO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 类别（大类）
     */
    private String category;

    /**
     * 类型（小类）
     */
    private String type;
}

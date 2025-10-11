package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学校类型
 *
 * @author wuketao
 * @date 2025/10/10
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolTypeVO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 是否为公办院校
     */
    private Boolean isPublic;
}

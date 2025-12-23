package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户摘要信息
 *
 * @author wuketao
 * @date 2025/12/23
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientVOSummary {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 中文名
     */
    private String chineseName;

    /**
     * 英文名
     */
    private String englishName;
}

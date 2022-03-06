package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 职位
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCaseAttentionVO {
    /**
     * 职位主键id
     */
    private Integer caseId;

    /**
     * 关注情况
     */
    private boolean attention;
}

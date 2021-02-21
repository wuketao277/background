package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 从旧职位上拷贝候选人到当前职位
 *
 * @author wuketao
 * @date 2021/02/21
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CopyFromOldCaseVO {
    /**
     * 当前职位id
     */
    private Integer curCaseId;

    /**
     * 旧职位id
     */
    private Integer oldCaseId;
}

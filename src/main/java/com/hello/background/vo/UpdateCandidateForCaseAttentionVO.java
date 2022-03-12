package com.hello.background.vo;

import lombok.Data;

/**
 * 更新推荐职位关联信息中的关注字段
 *
 * @author wuketao
 * @date 2022/3/12
 * @Description
 */
@Data
public class UpdateCandidateForCaseAttentionVO {
    /**
     * 关联主键
     */
    private Integer id;
    /**
     * 关注情况
     */
    private Boolean attention;
}

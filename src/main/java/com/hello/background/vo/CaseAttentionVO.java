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
public class CaseAttentionVO {
    /**
     * id
     */
    private Integer id;

    /**
     * 客户id
     */
    private Integer clientId;

    /**
     * 中文名
     */
    private String clientChineseName;

    /**
     * 职位id
     */
    private Integer caseId;

    /**
     * 职位名称
     */
    private String caseTitle;

    /**
     * 关注人（登录名）
     */
    private String userName;
}

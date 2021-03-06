package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 候选人与职位对应表
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateForCaseVO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 候选人id
     */
    private Integer candidateId;

    /**
     * 中文名字
     */
    private String chineseName;

    /**
     * 英文名字
     */
    private String englishName;

    /**
     * 职位id
     */
    private Integer caseId;

    /**
     * 职位名称
     */
    private String title;

    /**
     * 客户id
     */
    private Integer clientId;

    /**
     * 客户姓名
     */
    private String clientName;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    private String createUserName;

}

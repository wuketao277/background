package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Entity
public class CandidateForCase {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 候选人id
     */
    @Column
    private Integer candidateId;

    /**
     * 职位id
     */
    @Column
    private Integer caseId;

    /**
     * 客户id
     */
    @Column
    private Integer clientId;

    /**
     * 职位名称
     */
    @Column(length = 200)
    private String title;

    /**
     * 中文名字
     */
    @Column(length = 50)
    private String chineseName;

    /**
     * 英文名字
     */
    @Column(length = 100)
    private String englishName;

    /**
     * 状态
     */
    @Column(length = 100)
    private String status;

    /**
     * 创建时间
     */
    @Column
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    @Column(length = 50)
    private String createUserName;

}

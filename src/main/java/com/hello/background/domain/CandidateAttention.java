package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 关注候选人
 *
 * @author wuketao
 * @date 2022/10/19
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CandidateAttention {
    /**
     * id
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
     * 候选人中文名字
     */
    @Column(length = 50, nullable = false)
    private String candidateChineseName;
    /**
     * 用户ID
     */
    @Column
    private Integer userId;
    /**
     * 登录名
     */
    @Column(length = 50, nullable = false)
    private String userLoginName;
    /**
     * 真实姓名
     */
    @Column(length = 50, nullable = false)
    private String userRealName;
}

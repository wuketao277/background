package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 候选人简历
 * @author wuketao
 * @date 2021/02/16
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Resume {
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
     * 简历内容
     */
    @Column(length = 6000)
    private String content;

}

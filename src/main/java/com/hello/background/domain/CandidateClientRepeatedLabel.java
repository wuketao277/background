package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 候选人客户重复标签
 *
 * @author wuketao
 * @date 2026/2/20
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CandidateClientRepeatedLabel {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 名称
     */
    @Column(length = 20)
    private String name;
}

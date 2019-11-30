package com.hello.background.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 我的计划
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MySchedule {

    /**
     * 计划主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 计划的标题
     */
    @Column(length = 200, nullable = false)
    private String scheduleTitle;

    /**
     * 计划的内容
     */
    @Column(length = 2000, nullable = false)
    private String scheduleContent;

    /**
     * 计划的创建人ID
     */
    @Column(length = 50, nullable = false)
    private String scheduleUserId;

    /**
     * 计划的时间
     */
    @Column(nullable = false)
    private LocalDateTime scheduleDateTime;
}

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
 * 大事件
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BigEvent {

    /**
     * 事件id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 标题
     */
    @Column(length = 200, nullable = false)
    private String title;

    /**
     * 详细内容
     */
    @Column(length = 2000)
    private String detail;

    /**
     * 任务的创建人登录名
     */
    @Column(length = 50, nullable = false)
    private String createUserName;

    /**
     * 任务的创建人真实姓名
     */
    @Column(length = 50, nullable = false)
    private String createRealName;

    /**
     * 事件的创建时间
     */
    @Column(nullable = false)
    private LocalDateTime createDateTime;
}

package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 我的任务
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyTaskUpdateVO {

    /**
     * 任务主键id
     */
    private Integer id;

    /**
     * 是否完成
     */
    private Boolean finished = false;

    /**
     * 执行结果
     */
    private String executeResult;
}

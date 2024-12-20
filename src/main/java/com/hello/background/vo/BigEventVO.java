package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BigEventVO {
    /**
     * 事件id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 详细内容
     */
    private String detail;

    /**
     * 任务的创建人登录名
     */
    private String createUserName;

    /**
     * 任务的创建人真实姓名
     */
    private String createRealName;

    /**
     * 事件的创建时间
     */
    private LocalDateTime createDateTime;
}

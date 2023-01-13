package com.hello.background.vo;

import lombok.Data;

/**
 * @author wuketao
 * @date 2023/1/12
 * @Description
 */
@Data
public class MyTaskPageQuerySearchVO {
    private String finished;
    private String executeUserName;
    private String taskTitle;
    private String taskContent;
}

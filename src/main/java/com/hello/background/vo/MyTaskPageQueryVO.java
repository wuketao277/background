package com.hello.background.vo;

import lombok.Data;

/**
 * @author wuketao
 * @date 2023/1/12
 * @Description
 */
@Data
public class MyTaskPageQueryVO {
    private Integer currentPage;
    private Integer pageSize;
    private MyTaskPageQuerySearchVO search;
}

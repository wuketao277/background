package com.hello.background.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * pipeline对象
 *
 * @author wuketao
 * @date 2023/1/20
 * @Description
 */
@Data
public class PipelineVO {
    /**
     * 用户
     */
    private UserVO user;
    /**
     * 职位列表
     */
    private List<PipelineCaseVO> pipelineCaseList = new ArrayList<>();
}

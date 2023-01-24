package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pipeline候选人
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PipelineCandidateVO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 中文名字
     */
    private String chineseName;
}

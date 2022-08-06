package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 保存学习记录得分请求
 *
 * @author wuketao
 * @date 2022/8/6
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveStudyRecordScoreRequestVO {
    /**
     * 记录id
     */
    private Integer id;
    /**
     * 得分
     */
    private Integer score;
}

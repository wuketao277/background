package com.hello.background.vo;

import com.hello.background.constant.CaseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 案件
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCaseVO {
    /**
     * 新闻主键id
     */
    private Integer id;

    /**
     * 客户id
     */
    private Integer clientId;

    /**
     * 职位名称
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private CaseStatusEnum status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    private String createUserId;
}

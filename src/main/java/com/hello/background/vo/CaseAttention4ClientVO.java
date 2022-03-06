package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注职位 客户信息
 *
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseAttention4ClientVO {
    /**
     * 客户id
     */
    private Integer clientId;

    /**
     * 客户中文名
     */
    private String clientChineseName;

    /**
     * 职位集合
     */
    private List<CaseAttention4CaseVO> caseList = new ArrayList<>();
}

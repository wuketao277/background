package com.hello.background.vo;

import com.hello.background.constant.CaseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuketao
 * @date 2022/11/22
 * @Description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CaseQueryPageRequestSearch {
    private Integer clientId;
    private Integer hrId;
    private CaseStatusEnum status;
    private String title;
}

package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuketao
 * 生成下一个成功case的结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenNextContractingResponse {
    private boolean success = false;
    private String msg = "系统异常";
}

package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 保存用户基本信息返回结果
 *
 * @author wuketao
 * @date 2019/12/7
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserBasicInfoResponse implements Serializable {
    /**
     * 保存是否成功
     */
    private boolean result;
    /**
     * 异常信息
     */
    private String msg;
    /**
     * 用户信息
     */
    private UserVO userVO;
}

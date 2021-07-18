package com.hello.background.vo;

import lombok.Data;

/**
 * @author wuketao
 * @date 2021/7/18
 * @Description
 */
@Data
public class UpdatePassword {
    private String newPassword;
    private String oldPassword;
}

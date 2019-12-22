package com.hello.background.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuketao
 * @date 2019/12/22
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {
    private String loginName;
    private String password;
}

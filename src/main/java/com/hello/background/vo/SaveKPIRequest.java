package com.hello.background.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author wuketao
 * @date 2021/7/3
 * @Description
 */
@Data
public class SaveKPIRequest {
    private LocalDate month;
}

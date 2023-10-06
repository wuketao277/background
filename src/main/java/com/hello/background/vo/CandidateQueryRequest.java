package com.hello.background.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author wuketao
 * @Description
 */
@Data
public class CandidateQueryRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private String createUser;
}

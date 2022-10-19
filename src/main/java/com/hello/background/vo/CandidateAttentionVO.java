package com.hello.background.vo;

import lombok.Data;

/**
 * 关注候选人
 *
 * @author wuketao
 * @date 2022/10/19
 * @Description
 */
@Data
public class CandidateAttentionVO {
    /**
     * id
     */
    private Integer id;
    /**
     * 候选人id
     */
    private Integer candidateId;
    /**
     * 候选人中文名字
     */
    private String candidateChineseName;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 登录名
     */
    private String userLoginName;
    /**
     * 真实姓名
     */
    private String userRealName;
}

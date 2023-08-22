package com.hello.background.vo;

import com.hello.background.constant.CandidateSpecialItemEnum;
import com.hello.background.constant.GenderEnum;
import lombok.Data;

import java.util.List;

/**
 * 搜索候选人集合条件
 *
 * @author wuketao
 * @date 2023/4/13
 * @Description
 */
@Data
public class SearchCandidateListCondition {
    /**
     * 关键词集合
     */
    private String keyWords;

    /**
     * 性别
     */
    private GenderEnum gender;

    /**
     * 学校等级 211 985 双一流
     */
    private List<String> schoolLevel;

    /**
     * 最小年龄
     */
    private Integer ageMin;

    /**
     * 最大年龄
     */
    private Integer ageMax;

    /**
     * 最远阶段
     */
    private String farthestPhase;

    /**
     * 特殊项
     */
    private List<CandidateSpecialItemEnum> specialItem;

    /**
     * 顾问登录名
     */
    private String userName;
}

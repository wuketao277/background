package com.hello.background.constant;

import java.math.BigDecimal;

/**
 * KPI标准常量
 *
 * @author wuketao
 * @date 2023/4/18
 * @Description
 */
public class KPIStandardConstants {
    /**
     * KPI增加系数
     */
    public static BigDecimal plusRate = new BigDecimal(1.1111);
    /**
     * AM KPI得分计算
     * 1周KPI计算：VI+CVO+面试=总分 1周5天，每天100分，总共500分
     * VI占10%，总分是50分，要求10个，所以每个VI得5分
     * CVO占30%，总分是150分，要求3个，所以每个CVO得50分
     * 1面占60%，总分是300分，要求2.5个，所以每个1面的120分。每个2面得60分，每个3面得30分，每个4面得15分，每个5面得7.5分，每个6面得3.75分。
     */
    /**
     * AM VI+IOI 占比
     */
    public static BigDecimal amVIIOIPercent = new BigDecimal(0.1);
    /**
     * AM VI+IOI 每天数量
     */
    public static BigDecimal amVIIOICount = new BigDecimal(1);
    /**
     * AM 每个VI+IOI 得分
     */
//    public static BigDecimal amVIIOIPoint = new BigDecimal(5).multiply(plusRate);
    /**
     * AM CVO 占比
     */
    public static BigDecimal amCVOPercent = new BigDecimal(0.3);
    /**
     * AM CVO 每天数量
     */
    public static BigDecimal amCVOCount = new BigDecimal(0.6);
    /**
     * AM 每个CVO 得分
     */
//    public static BigDecimal amCVOPoint = new BigDecimal(50).multiply(plusRate);
    /**
     * AM 面试 占比
     */
    public static BigDecimal amInterviewPercent = new BigDecimal(0.6);
    /**
     * AM 第一轮面试 每天数量
     */
    public static BigDecimal am1stCount = new BigDecimal(0.5);
    /**
     * AM 第二轮面试 每天数量
     */
    public static BigDecimal am2ndCount = new BigDecimal(1);
    /**
     * AM 第三轮面试 每天数量
     */
    public static BigDecimal am3rdCount = new BigDecimal(2);
    /**
     * AM 第四轮面试 每天数量
     */
    public static BigDecimal am4thCount = new BigDecimal(4);
    /**
     * AM 第五轮面试 每天数量
     */
    public static BigDecimal am5thCount = new BigDecimal(8);
    /**
     * AM 第六轮面试 每天数量
     */
    public static BigDecimal am6thCount = new BigDecimal(16);
//    /**
//     * AM 每个第一轮面试 得分
//     */
//    public static BigDecimal am1stPoint = new BigDecimal(120).multiply(plusRate);
//    /**
//     * AM 每个第二轮面试 得分
//     */
//    public static BigDecimal am2ndPoint = new BigDecimal(60).multiply(plusRate);
//    /**
//     * AM 每个第三轮面试 得分
//     */
//    public static BigDecimal am3rdPoint = new BigDecimal(30).multiply(plusRate);
//    /**
//     * AM 每个第四轮面试 得分
//     */
//    public static BigDecimal am4thPoint = new BigDecimal(15).multiply(plusRate);
//    /**
//     * AM 每个第五轮面试 得分
//     */
//    public static BigDecimal am5thPoint = new BigDecimal(7.5).multiply(plusRate);
//    /**
//     * AM 每个第六轮面试 得分
//     */
//    public static BigDecimal amFinalPoint = new BigDecimal(3.75).multiply(plusRate);


    /**
     * AM KPI得分计算
     * 1周KPI计算：TI+VI+CVO+面试=总分 1周5天，每天100分，总共500分
     * TI占10%，总分是50分，要求100个，所以每个IT得0.5分
     * VI占10%，总分是50分，要求10个，所以每个VI得5分
     * CVO占30%，总分是150分，要求3个，所以每个CVO得50分
     * 1面占50%，总分是250分，要求2个，所以每个1面的125分。每个2面得62分，每个3面得31分，每个4面得15分，每个5面得7.5分，每个6面得3.75分。
     */
    /**
     * Recruiter TI+CF 占比
     */
    public static BigDecimal reTICFPercent = new BigDecimal(0.1);
    /**
     * Recruiter TI+CF 每天数量
     */
    public static BigDecimal reTICFCount = new BigDecimal(20);
    /**
     * Recruiter 每个TI+CF 得分
     */
//    public static BigDecimal reTICFPoint = new BigDecimal(0.5).multiply(plusRate);
    /**
     * Recruiter VI+IOI 占比
     */
    public static BigDecimal reVIIOIPercent = new BigDecimal(0.1);
    /**
     * Recruiter VI+IOI 每天数量
     */
    public static BigDecimal reVIIOICount = new BigDecimal(1);
    /**
     * Recruiter 每个VI+IOI 得分
     */
//    public static BigDecimal reVIIOIPoint = new BigDecimal(5).multiply(plusRate);
    /**
     * Recruiter CVO 占比
     */
    public static BigDecimal reCVOPercent = new BigDecimal(0.3);
    /**
     * Recruiter CVO 每天数量
     */
    public static BigDecimal reCVOCount = new BigDecimal(0.6);
    /**
     * Recruiter 每个CVO 得分
     */
//    public static BigDecimal reCVOPoint = new BigDecimal(50).multiply(plusRate);
    /**
     * Recruiter 面试 占比
     */
    public static BigDecimal reInterviewPercent = new BigDecimal(0.5);
    /**
     * Recruiter 第一轮面试 每天数量
     */
    public static BigDecimal re1stCount = new BigDecimal(0.4);
    /**
     * Recruiter 第二轮面试 每天数量
     */
    public static BigDecimal re2ndCount = new BigDecimal(0.8);
    /**
     * Recruiter 第三轮面试 每天数量
     */
    public static BigDecimal re3rdCount = new BigDecimal(1.6);
    /**
     * Recruiter 第四轮面试 每天数量
     */
    public static BigDecimal re4thCount = new BigDecimal(3.2);
    /**
     * Recruiter 第五轮面试 每天数量
     */
    public static BigDecimal re5thCount = new BigDecimal(6.4);
    /**
     * Recruiter 第六轮面试 每天数量
     */
    public static BigDecimal re6thCount = new BigDecimal(12.8);
//    /**
//     * Recruiter 每个第一轮面试 得分
//     */
//    public static BigDecimal re1stPoint = new BigDecimal(125).multiply(plusRate);
//    /**
//     * Recruiter 每个第二轮面试 得分
//     */
//    public static BigDecimal re2ndPoint = new BigDecimal(62).multiply(plusRate);
//    /**
//     * Recruiter 每个第三轮面试 得分
//     */
//    public static BigDecimal re3rdPoint = new BigDecimal(31).multiply(plusRate);
//    /**
//     * Recruiter 每个第四轮面试 得分
//     */
//    public static BigDecimal re4thPoint = new BigDecimal(15).multiply(plusRate);
//    /**
//     * Recruiter 每个第五轮面试 得分
//     */
//    public static BigDecimal re5thPoint = new BigDecimal(7.5).multiply(plusRate);
//    /**
//     * Recruiter 每个第六轮面试 得分
//     */
//    public static BigDecimal reFinalPoint = new BigDecimal(3.75).multiply(plusRate);
}

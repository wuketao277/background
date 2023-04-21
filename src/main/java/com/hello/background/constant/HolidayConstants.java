package com.hello.background.constant;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * 假期常量类
 *
 * @author wuketao
 * @date 2023/4/21
 * @Description
 */
@Data
public class HolidayConstants {
    /**
     * 2023节假日
     */
    public static List<String> holiday2023 = Arrays.asList("1月2日", "1月3日", "1月21日", "1月22日", "1月23日", "1月24日", "1月25日", "1月26日", "1月27日", "4月5日", "4月29日", "4月30日", "5月1日", "5月2日", "5月3日", "6月22日", "6月23日", "6月24日", "9月29日", "9月29日", "9月30日", "10月1日", "10月2日", "10月3日", "10月4日", "10月5日", "10月6日");
    /**
     * 2023周末调休
     */
    public static List<String> workday2023 = Arrays.asList("1月28日", "1月29日", "4月23日", "5月6日", "6月25日", "10月7日", "10月8日");
}
